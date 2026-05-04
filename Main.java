import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	Controlador controlador = new Controlador();
	
	System.out.println("=== Buscador de palabras ===");
	
	try {
	    String ruta = controlador.recibeRuta();
	    controlador.preparaRuta(ruta);
	} catch (IllegalArgumentException e) {
	    System.err.println("Error con la ruta: " + e.getMessage());
	    sc.close();
	    return;
	}
	
	String seguir = "s";
	while (!seguir.equals("n")) {
	    try {
		controlador.empiezaBusqueda();
	    } catch (Exception e) {
		System.err.println("Error en la busqueda: " + e.getMessage());
	    }

		do{
			System.out.print("Deseas buscar otra palabra? (s/n): ");
	    	seguir = sc.nextLine().trim().toLowerCase();
			if(!seguir.equals("s") && !seguir.equals("n")){
				System.out.println("ESCRIBE 's' o 'n' plox :)) ");
			}
		}while(!seguir.equals("s") && !seguir.equals("n"));
		
	}
	
	System.out.println("Bye bye");
	sc.close();
    }
}
