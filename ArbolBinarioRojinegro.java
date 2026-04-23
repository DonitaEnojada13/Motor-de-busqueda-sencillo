public class ArbolBinarioRojinegro<T extends Comparable<T>> {

    private class VerticeRn {
	
	public T elemento;
	public Color color;
	
	public VerticeRn padre;
	public VerticeRn izquierdo;
	public VerticeRn derecho;


	public VerticeRn(T elemento) {
	    this.elemento = elemento;
	    this.color = Color.ROJO;
	}

	/**
	 * Metodos para ahorrar dolores de cabeza y  
	 * ser usados posteriormente
	 */
	
	public boolean hayPadre() {
	    return padre != null;
	}
	public boolean hayIzquierdo() {
	    return izquierdo != null;
	}
	public boolean hayDerecho() {
	    return derecho != null;
	}
	
	public T get() {
	    return elemento;
	}

	/**
	 * Son metodos que tratan de
	 * regresar los mismos vertices
	 * y sus caracteristicas
	 */
	public VerticeRn padre() {
	    return padre;
	}
	public VerticeRn izquierdo() {
	    return izquierdo;
	}
	public VerticeRn derecho() {
	    return derecho;
	}
	public Color getColor() {
	    return color;
	}	
    }

    private VerticeRn raiz;
    private VerticeRn ultimoAgregado;
    private int elementos;

    /**
     * Constructor vacio (Inicializa los atributos en null y 0 por defecto)
     */
    public ArbolBinarioRojinegro() {}
    
    public boolean esRaiz(Vertice vertice){
        return vertice == raiz;
    }

    public boolean esHijoIzquierdo(VerticeRn a) {
	if (a == null || !a.hayPadre())
	    return false;
	return a.padre().izquierdo() == a;
    }
    public boolean esHijoDerecho(VerticeRn b) {
	if (b == null || !b.hayPadre())
	    return false;
	return b.padre().derecho() == b;
    }

    private boolean esHoja(VerticeRn vertice){
        return !vertice.hayDerecho() && !vertice.hayIzquierdo();
    }

    private boolean soloTieneUnHijo(VerticeRn vertice){
        return !vertice.hayDerecho() && vertice.hayIzquierdo() || vertice.hayDerecho() && !vertice.hayIzquierdo() ;
    }

    private boolean tieneAmbosHijos(VerticeRn vertice){
        return vertice.hayDerecho() && vertice.hayIzquierdo();
    }

    public void giroD(VerticeRn q) {

	if(q == null || !q.hayIzquierdo())
	    return;
	
	VerticeRn p = q.izquierdo();
	p.padre = q.padre();

	if (q.hayPadre()) {
	    if (esHijoIzquierdo(q))
		q.padre().izquierdo = p;
	    else
		q.padre().derecho = p;	
	} else {
	    raiz = p;
	}
	
	q.izquierdo = p.derecho();
	if (p.hayDerecho())
	    p.derecho().padre = q;
	
	p.derecho = q;
	q.padre = p;
    }

    public void giroI(VerticeRn p) {
	if (p == null || !p.hayDerecho())
	    return;
	VerticeRn q = p.derecho();
	q.padre = p.padre();

	if(p.hayPadre()) {
	    if (esHijoDerecho(p))
		p.padre().derecho = q;
	    else
		p.padre().izquierdo = q;
	} else {
	    raiz = q;
	}
	p.derecho = q.izquierdo();
	if(p.hayDerecho())
	    p.derecho().padre = p;
	q.izquierdo = p;
	p.padre = q;
    }

    /**
     * Metodo privado que emula la introduccion de ArbolOrdenado, donde los elementos 
     * menores o iguales van a a la izquierda y los mayores a la derecha 
     */

    public void agregaRn(T elemento) {
    }
    private void meteAB(VerticeRn dentro, T  mete) {
	
	if(mete.compareTo(dentro.get()) <= 0) {
	    if (!dentro.hayIzquierdo()) {
		VerticeRn vertice = new VerticeRn(mete);
		vertice.padre = dentro;
		dentro.izquierdo = ultimoAgregado = vertice;
		
	    } else {
		meteAB(dentro.izquierdo(), mete);
	    }
	} else {
	    if (!dentro.hayDerecho()) {
		VerticeRn vertice = new VerticeRn(mete);
		vertice.padre = dentro;
		dentro.derecho = ultimoAgregado = vertice;
	    } else {
		meteAB(dentro.derecho(), mete);
	    }
	}
    }
    
    private void rebalanceo()

    
}
