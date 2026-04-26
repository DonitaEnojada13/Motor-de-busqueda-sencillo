import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.Normalizer;
import java.io.IOException;

public class LectorArchivo {
    //TO DO

    /* Este es el molde de una clase que permita la lectura de archivos de texto plano .txt,
     supongo que debe de tener un metodo que capte las palabras una por una. De manera que,
    si tenemos "Hola que tal?" la clase sea capaz de romper eso en:
         -hola
	 -que
	 -tal
    Se van a ignorar los caracteres especiales y los puntos tambien, podemos aprovecharnos de
    equalsIgnoreCase() (Creo que es ese) para poder detectar las peculiaridades del texto.
    
    */
    private File archivoActual;

    public void leerArchivo(File archivo, ){
	if (archivo == null || archivo.exists())
	    System.err.println("Archivo no valido o null");
            return;

        //Esto es lo que vamos a cambiar porque esta mal lol 
        try(BufferedReader br = new BufferedReader(new FileReader(archivo))){
	    
            String lineaActual;
	    
            while((lineaActual = br.readLine()) != null){
                String lineaLimpia = normalizador(lineaActual);

		// "\\s+" De acuerdo con Class Pattern, se tiene que
		// "\\s" un espcio en blanco
		// "\\s+" uno o mas espacios en blancos

		// Se crea un areglo de String que muere al terminar este ciclo, bye bye popo
		String[] palabras = lineaLimpia.trim().split("\\s+");

		for(String palabra: palabras) {
		    if (palabra != null && !palabra.isEmpty())
			// cositas
			}
	    }
	} catch (IOException e) {
	    System.err.println("Error al leer el archivo: " + e.getMessage());
	}
    }
    
    /**
     * Metodo privado para poder evitar letras con acentos o 
     * cosas raras. No los puedo poner poque no se como ponerlos en
     * mi lap
     * @param String de entrada por normalizar
     * @return String normalizado sin acentos y minus/mayus
     */
    private String normalizador(String s) {
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
    
    public void recibir(String p){
        
    }
}
