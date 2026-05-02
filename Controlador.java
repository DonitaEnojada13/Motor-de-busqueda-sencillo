import java.util.Scanner;
import java.text.Normalizer;
import java.io.File;

public class Controlador {

    private Scanner sc;
    private ArbolBinarioRojinegro<String> indice;
    private LectorArchivo lector;
    private Filtro filtro;
    private int totalArchivos;

    /**
     * Metodo para recibir el string de la
     * consulta
     * @param String consulta
     * @return String
     */

    public Controlador() {
        this.sc = new Scanner(System.in);
        this.indice = new ArbolBinarioRojinegro<>();
        this.lector = new LectorArchivo();
	this.filtro = new Filtro();
	this.totalArchivos = 0;
	
    }
    
    public void preparaRuta(String rutaRecibida) {
        LectorRuta lectorRuta = new LectorRuta(rutaRecibida);
	
        if (lectorRuta.esUsable()) {
            File[] archivos = lectorRuta.listadoDocs();
	    totalArchivos = archivos.length;
            
            System.out.println("Preparando la cantidad de " + archivos.length + " archivos");
            
            for (File f : archivos) {
		lector.leerArchivo(f, indice);
            }
            System.out.println("Archivos procesados de manera exitosa");
        } else {
            System.err.println("La ruta no es valida o no es un directorio");
        }
    }
	
    /////// Faltan cosas no se si esten bien
    public void empiezaBusqueda() {

	// El string para ir buscando, pero despues de la normalizacion y limpieza
	String s = recibeConsulta();
	// Separamos el string en un arreglo de palabaras, spearando por espacios en
        // blanco => Adrian/Pro/Gamer => [Adrian, Pro, Gamer]
	String[] palabras = s.split("\\s+");
	
    // Primero imprimimos qué se está buscando
	for (String p : palabras)
	    if (!p.isEmpty())
	    // Este es un consejo que me dio el profe para que vean que se esta buscando
            // la palabra ya limpia, si el usuario mete "*&^%&**Hola", se impirimira
            // Buscando: "hola", porque asi depura el metodo normalizar
		System.out.println("Buscando: " + p);
    
	ListaLigada<ResultadoBusqueda> resultados =
	    filtro.procesarBusqueda(palabras, indice, totalArchivos);
	
	if (resultados == null || !resultados.iterator().hasNext()) {
	    System.out.println("Sin resultados.");
	} else {
	    for (ResultadoBusqueda r : resultados)
		// Estaba viendo si printf hace las cosas bonitas en la terminal, creo qeu si
		System.out.printf("%-30s peso: %.4f%n", r.documento.getName(), r.valorFinal);
	}
    }
    
    public String recibeRuta() {
	String ruta = "";

	while(ruta.isEmpty()) {
	    System.out.println("Ingresa la ruta que deseas");
            ruta = sc.nextLine().trim();
	    if (ruta.isEmpty()) {
		System.out.println("Aqui no aceptamos rutas vacias, bobo");
	    } else {
		LectorRuta checarR = new LectorRuta(ruta);
		if (!checarR.esUsable()) {
		    System.out.println("Ruta inexistente o no es carpeta. Intenta de nuevo");
		    ruta = "";
		}
	    }
	}
	return ruta;
    }
    
    public String recibeConsulta() {
        String consulta = "";

        // Mientras el nombre sea vacio que se haga esta accion
        while(consulta.isEmpty()) {    
            System.out.println("Ingresa tu consulta");
            consulta  = sc.nextLine().trim();
            
            if(consulta.isEmpty()){
            System.out.println("Aqui no aceptamos consultas vacias, bobo");
            }
        }
        return normalizarC(consulta);
    }


    /**
     * Reutilizar la logica de normalizar de la clase 
     * lector archivo, pero en este caso se aplica para 
     * la consulta.
     * 
     */
    private String normalizarC(String s) {
        if (s == null)
            return "";
        // Instancia de clase Normalizer
        // metodo normalizar (Recibe el String, el tipo de normalizacion)
        // Investigando vi que hay una separacion entre NFC Y NFD, pero creo que NFD
        // es mas util para el proyecto, ya que separa las palabaras, es decir
        // Héctor  =>  H + e + ´ + c + t + o + r
        String sPrima = Normalizer.normalize(s, Normalizer.Form.NFD);
        
        StringBuilder stb = new StringBuilder();
        
        for(int i = 0; i < sPrima.length(); i++) {
            char c = sPrima.charAt(i);
            // Wrapper de Caracter para ver si es letra/numero o espacio
            // Aqui se hace el descarte de manera directa
	    
            // SI la palabra fuera Jaimito111_pro, lo descompone en
            // jaimito111pro, pero hay que ver si lo dejamos todo junto o hacemos que agregue un espacio
            // jaimito111 pro,mmmmmmmmmmm
            if(Character.isLetterOrDigit(c) || Character.isWhitespace(c))
		    stb.append(Character.toLowerCase(c));
            // para meterle un espacio y tener jaimito111 pro, mas palabras pero creo que funciona
	    
            // ***Ver que hacer si tenemos "Hola /mundo" == "hola  mundo" dos espacios?
            else stb.append(' ');
        }
        return stb.toString();
    }

}
