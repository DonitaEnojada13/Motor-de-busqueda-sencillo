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
    private File archivoActual;

    public ArbolBinarioRojinegro agregaPalabras(File textoAgregar){
        ArbolBinarioRojinegro <String> organizarPalabras = new ArbolBinarioRojinegro<>();

        try(BufferedReader rd = new BufferedReader(new FileReader(textoAgregar))){
            String palabra;
            while((palabra = br.readLine()) != null){
                organizarPalabras.agregaRn(palabra.trim());
            }

        }catch(IOException e){
            e.printStackTrace();
        }

        return organizarPalabras;
    }
    
}
