import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaLigada<T extends Comparable<T>> implements Iterable<T>{

    private class Nodo{
        public Nodo anterior, siguiente;
        public T elemento;

        public Nodo(T elemento){
            if(elemento == null ) throw new NullPointerException ("Error");
            this.elemento = elemento;
        }
        //Métodos get y set del nodo

        public Nodo getAnterior(){
            return anterior;
        }
        
        public void setAnterior(Nodo anterior){
            this.anterior = anterior;
        }

        public Nodo getSiguiente(){
            return siguiente;
        }

        public void setSiguiente(Nodo siguiente){
            this.siguiente = siguiente;
        }

        public T getElemento(){
            return elemento;
        }

        public void setElemento(T elemento){
            this.elemento = elemento;
        }
    }

    private Nodo cabeza, rabo;
    private int size;

    @Override
    public Iterator<T> iterator(){
        return new IteradorLista();
    }

    public void agregar(T elemento){
        if(elemento == null) throw new NullPointerException("No hay elemento a añadir");
        Nodo nodoAgregar = new Nodo(elemento);

        if(esVacia()){
            agregarVacio(nodoAgregar);  
        }

        nodoAgregar.setAnterior(rabo);
        rabo.setSiguiente(nodoAgregar);
        nodoAgregar.setSiguiente(null);
        rabo = nodoAgregar;
        size++;

    }

    public void elimina(T elemento){
        if(elemento == null || this.cabeza == null) throw new NullPointerException("No hay nada");

        Nodo actual = cabeza;
    
        while(actual != null){
            if(actual.getElemento().equals(elemento)){
                
                //es la cabeza?
                if(actual.getAnterior() != null){
                    actual.getAnterior().setSiguiente(actual.getSiguiente());
                }else{
                    cabeza = actual.getSiguiente();
                }

                //Es el rabo?
                if(actual.getSiguiente() != null){
                    actual.getSiguiente().setAnterior(actual.getAnterior());
                }else{
                    rabo = actual.getAnterior();
                }

                size--;
                return;
            }
            actual = actual.getSiguiente();
        }
        
    }

    public T busca(T elemento){
        if(elemento == null || this.cabeza == null) return null;

        Nodo actual = cabeza; 
        while(actual != null){
            if(actual.getElemento().equals(elemento)) return actual.getElemento();

            actual = actual.getSiguiente();
        }
        return null;
    }
    
    public boolean contains(T elemento){
        if(elemento == null || this.size == 0) return false;
        
        Nodo actual = this.cabeza;
        while(actual != null){
            if(actual.getElemento() == elemento) return true;
        
            actual = actual.getSiguiente();
        }
        return false;
    }

    //Métodos de apoyo
    private void agregarVacio(Nodo agregar){
        this.cabeza = agregar;
        this.rabo = agregar;
        agregar.setSiguiente(null);
        agregar.setAnterior(null);

    }

    private boolean esVacia(){
        return this.cabeza == null && this.rabo == null;
    }


    public class IteradorLista implements Iterator<T>{
        private Nodo actual;

        public IteradorLista(){
            this.actual = cabeza;
        }

        @Override
        public boolean hasNext(){
            return actual != null;
        }

        @Override
        public T next(){
                if(!hasNext()) throw new NoSuchElementException("No hay elementos en la lista");
            

            T dato = actual.getElemento();
            actual = actual.getSiguiente();
            return dato;
        }
    }
}