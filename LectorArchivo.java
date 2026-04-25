import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

    public void leerArchivo(File archivo,  ArbolBinarioRojinegro<String> a){

        //Esto es lo que vamos a cambiar porque esta mal lol 
        try(BufferedReader br = new BufferedReader(new FileReader(archivo)))){
            String lineaActual;
            while((lineaActual = br.readLine()) != null){
                String lineaSinEspeciales = lineaActual.replaceAll("[^a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ]", "");

                lineaSinEspeciales = lineaSinEspeciales.trim();

                if(!lineaSinEspeciales.isEmpty()){
                    String[] palabras = lineaSinEspeciales.split("\\s+");

                    for(String palabra : palabras){
                        a.agregaRn(palabra);
                    }
                }
            }
        }catch(IOException e){
            System.err.println("Error en: " + e.getMessage());
        }

    }
    
}
