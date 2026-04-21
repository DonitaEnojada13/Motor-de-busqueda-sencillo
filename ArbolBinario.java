import java.util.NoSuchElementException;

public abstract class ArbolBinario<T> {

    private class Vertice implements VerticeArbolBinario<T> {
	public T elem;
	public Vertice padre;
	public Vertice izquierdo;
	public Vertice derecho;

	public Vertice(T elemento) {
	    this.elem = elemento;
	}

	/**
	* Metodos del contrato de VerticeArbolBinario<T> por si solos no dan mucha informacion
	* pero seran aprovechados para la parte de arbol binario de busqueda y rojinegros
	*/
	@Override public boolean hayPadre() {
	    return padre != null;
	}
	@Override public boolean hayIzquierdo() {
	    return izquierdo != null;
	}
	@Override public boolean hayDerecho() {
	    return derecho != null;
	}
	@Override public VerticeArbolBinario<T> padre() {
	    if (!hayPadre())
		throw new NoSuchElementException("El padre es null, espabila");
	    return padre;
	}
	@Override public VerticeArbolBinario<T> izquierdo() {
	    if (!hayIzquierdo())
		throw new NoSuchElementException("El izq es null, espabila");
	    return izquierdo;
	}
	@Override public VerticeArbolBinario<T> derecho() {
	    if (!hayDerecho())
		throw new NoSuchElementException("El derecho es null, espabila");
	    return derecho;
	}
	@Override public int altura() {
	    return rAltura(this);
	}
	private int rAltura(Vertice a) {
	    if (a == null)
		return -1;
	    return 1+Math.max(rAltura(a.izquierdo), rAltura(a.derecho));
	}

	@Override public int profundidad() {
	    return rProfundidad();
	}
	private int rProfundidad() {
	    if (!hayPadre())
		return 0;
	    return 1+ padre.rProfundidad();
	}
	@Override public T get() {
	    return this.elem;
	}
	@Override public boolean equals(Object o) {
	    if (o == null || getClass() != o.getClass())
		return false;
	    @SuppressWarnings("unchecked") Vertice vertice = (Vertice) o;
	    return this.elem.equals(vertice.elem) 
		&& equals(this.izquierdo, vertice.izquierdo)
		&& equals(this.derecho, vertice.derecho);
	}
	private boolean equals(Vertice a, Vertice b) {
	    if (a == null && b == null)
		return true;
	    if (a == null || b == null)
		return false;
	    
	    return  a.elem.equals(b.elem)
		&& equals(a.izquierdo, b.izquierdo)
		&& equals(a.derecho, b.derecho);
	}
    }
    
    //Inicio de la clase del Árbol
    private Vertice raiz;
    private int elementos;

    public ArbolBinario() {}
    
    public Vertice nuevoVertice(T elemento) {
        return new Vertice(elemento);
    }

    public Vertice vertice(VerticeArbolBinario<T> vertice) {
        return (Vertice) vertice;
    }

    /**
     * Creo que dejare los metodos que si vayamos a ocupar en la
     * implementacion de todo lo que sera el arbol rojinegro
     */

    public boolean esVacia() {
	return raiz == null;
    }

    /**
     * Este metodo es para ver si el elemento esta en el arbol (Ojo, solo el elemento, no el vertice)
     * para determinar si el vertice se encuentra en el arbol, se hara un metodo aparte.
     */
    public boolean contiene(T elemento) { 
        if(esVacia())
            return false;
        return auxContiene(raiz, elemento);
    }

    /**
     * Metodo auxiliar para contiene, ajua
     */
    private boolean auxContiene(Vertice vertice, T elemento){
        if(vertice == null) return false;
        if(vertice.get().equals(elemento)) return true;

        if(vertice.izquierdo != null && vertice.derecho != null){
            return auxContiene(vertice.izquierdo, elemento) || auxContiene(vertice.derecho, elemento); 
        }
        return vertice.izquierdo != null ? auxContiene(vertice.izquierdo, elemento) : auxContiene(vertice.derecho, elemento);
    }

    
    // Metodos para buscar el vertice dado un elemento, mi debilidad siempre han sido los algoritmos de busqueda
    // este codigo es implementacion de Cisco (una gran persona)
    
    public VerticeArbolBinario<T> busca(T elemento) {
        return auxBusca(raiz, elemento);
    }

    /**
     * Varios casos para este metodo,
     * 1. vertice de entrada null, regresa null
     * 2. el primero donde te posas es el que buscas, regresar ese vertice
     * 3. este es el mas complejo:
     *     3.1 si tienes dos hijos y son distintos de null, recursion sobre ellos
     *     3.1.1 si la recursion te da null en ambos, devuelve null
     *     3.1.2 en otro caso, revisa si alguno de los dos no es null y lo regresa
     * 4. el caso donde uno de sus hijos es distinto de null y el otro no, se hace
     *    recursion sobre ese hijo y continua
     */
    private VerticeArbolBinario<T> auxBusca(Vertice vertice, T elemento){
        if(vertice == null) return null;
        if(vertice.get().equals(elemento)) return vertice; 
	
        if(vertice.izquierdo != null && vertice.derecho != null){
            Vertice recIzquierda = (Vertice) auxBusca(vertice.izquierdo, elemento);
            Vertice recDerecha = (Vertice) auxBusca(vertice.derecho, elemento);
            if(recIzquierda == null && recDerecha == null) return null;
            return recIzquierda != null ? recIzquierda : recDerecha;
        }
        return vertice.izquierdo != null ? auxBusca(vertice.izquierdo, elemento) : auxBusca(vertice.derecho, elemento);
    }

    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
            ArbolBinario<T> arbol = (ArbolBinario<T>)objeto;
	
        if(!esVacia() && !arbol.esVacia())
            return raiz.equals(arbol.raiz);
        return esVacia() && arbol.esVacia(); 
    }
    
}
