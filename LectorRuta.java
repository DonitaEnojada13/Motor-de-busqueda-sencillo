
import java.io.File;
import java.io.FileFilter;

public class LectorRuta {
        
    /* Pense en esta clase como un punto de apoyo al iniciar el programa, es decir, cuando se inicia el programa,
     esta clase sera la encargada de pedir la ruta donde se alojan los archivos .txt que vamos a ocupar.

    AHora bien, supongo que hay que blindarlo contra cosas como:
           - Una ruta que no exista
	   - Una ruta que no tenga nada (No esta mal pero no serviria de mucho)
	   - Una ruta que no contenga archivos txt (No se si esa responsabilidad recae sobre esta clase o LectorArchivo, ya veremos)*/

    private File ruta;

    /**
     * Constructor de File
     * @param El string que el usuario nos debe de pasar para crear la ruta
     * @throws IllegalArgumentException si el string es null (No subestimar al usuario)
     */
    
    public LectorRuta(String nRuta) {

	if (nRuta == null)
	    throw new IllegalArgumentException("La ruta no pude ser null");
	if(nRuta.isEmpty())
	    throw new IllegalArgumentException("La ruta no pude ser vacia");
	    
	this.ruta = new File(nRuta);
    }

    /**
     * Getters de toda la vida
     */
    public File getRuta() {
	return this.ruta;
    }
    
    /**
     * Este metodo es una combinacion de dos metodos .exists() y .isDirectory()
     * con un retorno booleano. 
     */
    
    public boolean esUsable() {
	if (!ruta.exists() ||  !ruta.isDirectory())
	    return false;
	return true;
    }

    
    /**
     * Este metodo para regresar un arreglo de Archivos que cumplen con la particularidad de ser .txt
     * La verdad estoy probando los lambdas, asi que no se si eso sea correcto
     * @param la ruta de donde se recabaran todos los archivos con el filtro
     */
    

    public File[] listadoDocs() {
	// reusamos la validacion anterior para evitar problemas
	if (!esUsable())
	    return new File[0];

	// Una lambda que recurre al metodo listFiles, el cual devuelve un arreglo de File[],

	// Como tal, listFiles recibe un filtro de tipo FileFilter, entonces, aqui entra en accion el lambda
	// (nombreArchivo -> es un archivo && nombreArchivo.minuscula.terminacon .txt)
	File [] temp = ruta.listFiles(archivo -> archivo.isFile() && archivo.getName().toLowerCase().endsWith(".txt"));

	if (temp == null)
	    return new File[0];
	return temp;
    }
    
}
