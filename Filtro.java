import java.io.File;
import java.util.Iterator;

public class Filtro{
    private Peso calcularPeso;
    private ListaLigada<NormaDoc> listaNormas;

    public Filtro(){
        this.calcularPeso = new Peso();
        this.listaNormas = new ListaLigada<>();
    }

    /**
     * Este será el método que va a llamarse desde Conrtolador ok?
     * ahí me dices si son cosas que ni al caso o
     * @param palabrasFiltro que es para poder tener si el usuario pone algo como "Pepe pica papas"
     * @param busqueda que es para tener un indice del árbol rojinegro
     * @param totalArchivos para la cantidad total de .txt esto para el IDF
     * @return Una lista ligada ya ordenada de mayor a menos relevancia 
     */
    public ListaLigada<ResultadoBusqueda> procesarBusqueda(String[] palabrasFiltro, ArbolBinarioRojinegro<String> busqueda, int totalArchivos){
        
        ListaLigada<ResultadoBusqueda> resultadoFiltrados = new ListaLigada<>();

        for(String palabra : palabrasFiltro){
            ListaLigada<FrecuenciaPalabra> archivosAparece = busqueda.obtenerRepeticiones(palabra);

            if(archivosAparece != null){
                
                int docsConLaPalabra = 0;
                for(FrecuenciaPalabra freq : archivosAparece){
                    docsConLaPalabra++;
                }

                for(FrecuenciaPalabra freq : archivosAparece){
                    double tf = calcularPeso.calcularTF(freq.getApariciones());
                    double idf = calcularPeso.calcularIDF(totalArchivos, docsConLaPalabra);
                    double tfIDF = calcularPeso.calcularPeso(tf, idf);

                    actualizarLista(resultadoFiltrados, freq.getDocumento(), tfIDF);
                }
            }
        }

        for(ResultadoBusqueda res : resultadoFiltrados){
            for(NormaDoc norma : this.listaNormas){
                if(res.documento.equals(norma.archivo)){
                    res.valorFinal = calcularPeso.calcularSim(res.valorFinal, norma.sumaCuadrados);
                }
            }
        }

        QuickSort ordenador = new QuickSort();
        resultadoFiltrados = ordenador.ordenar(resultadoFiltrados);

        ListaLigada<ResultadoBusqueda> topBusquedas = new ListaLigada<>();
        int contador = 0;
        for(ResultadoBusqueda r : resultadoFiltrados){
            if(contador >= 10) break;

            if(r.valorFinal > 0){
                topBusquedas.agregarFinal(r);
                contador++;
            }
        }

        return topBusquedas;
    }

    public void preCalcularNormas(ArbolBinarioRojinegro<String> busqueda, int total){
        this.listaNormas = new ListaLigada<>();
        busqueda.calcularNormas(this.listaNormas, total);
    }

    private void actualizarLista(ListaLigada<ResultadoBusqueda> lista, File doc, double score){
        
        for(ResultadoBusqueda res : lista){
            if(res.documento.equals(doc)){
                res.valorFinal += score;
                return;
            }
        }

        lista.agregarFinal(new ResultadoBusqueda(doc, score));
    }



    //Aquí mejor creo una nueva clase privada para el quicksort, estaba viendo que se puede hacer directamente con las listas ligadas
    //Creamos dos y de ahí las vamos acomodando con recursion wacha
    private class QuickSort{

        public ListaLigada<ResultadoBusqueda> ordenar(ListaLigada<ResultadoBusqueda> lista){

            //Caso base, si esto es NULL o no tiene elementos acabamos mi pa con una nueva lista
            //Ocupamos el iterador que se creó en la clase de la lista ligada 
            if(lista == null) return new ListaLigada<>();

            Iterator<ResultadoBusqueda> iterador =  lista.iterator();

            if(!iterador.hasNext()) return lista; 


            //El primer elemento como pivote jiljrieowqjrio
            ResultadoBusqueda pivote = iterador.next();

            if(!iterador.hasNext()) return lista; //solo hay un elemento

            ListaLigada<ResultadoBusqueda> mayor = new ListaLigada<>();
            ListaLigada<ResultadoBusqueda> menor = new ListaLigada<>();

            while(iterador.hasNext()){
                ResultadoBusqueda actual = iterador.next();

                //aquí inicia a acomodar los mayores en una lista u en otra
                if(actual.valorFinal >= pivote.valorFinal){
                    mayor.agregarFinal(actual);
                }else{
                    menor.agregarFinal(actual);
                }
            }

            //La parte recursiva
            mayor = ordenar(mayor);
            menor = ordenar(menor);

            //Ya unimos ambas listas
            ListaLigada<ResultadoBusqueda>  listaFinal = new ListaLigada<>();

            for(ResultadoBusqueda doc : mayor){
                listaFinal.agregarFinal(doc);
            }

            listaFinal.agregarFinal(pivote);

            for(ResultadoBusqueda doc : menor){
                listaFinal.agregarFinal(doc);
            }

            return listaFinal;
        }

    }
}