import java.io.File;

public class LectorRuta {
    
    /* Pense en esta clase como un punto de apoyo al iniciar el programa, es decir, cuando se inicia el programa,
     esta clase sera la encargada de pedir la ruta donde se alojan los archivos .txt que vamos a ocupar.

    AHora bien, supongo que hay que blindarlo contra cosas como:
           - Una ruta que no exista
	   - Una ruta que no tenga nada (No esta mal pero no serviria de mucho)
	   - Una ruta que no contenga archivos txt (No se si esa responsabilidad recae sobre esta clase o LectorArchivo, ya veremos)*/

    private File[] docs;
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
     * Este metodo es una combinacion de dos metodos .exists() y .isDirectory()
     * con un retorno booleano.
     */
    
    public boolean esUsable() {
	if (!ruta.exists() ||  !ruta.isDirectory())
	    return false;
	return true;
    }
    
}
