public class ArbolBinarioRojinegro<T extends Comparable<T>> {

    private class VerticeRn {
	
	public T elemento;
	public Color color;
	
	public VerticeRn padre;
	public VerticeRn izquierdo;
	public VerticeRn derecho;


	public VerticeRn(T elemento) {
	    this.elemento = elemento;
	    this.color = Color.NINGUNO;
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
    
    public boolean esRaiz(VerticeRn vertice){
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
	meteAB(elemento);
	ultimoAgregado.color = Color.ROJO;
	rebalanceoMete(ultimoAgregado);
    }

    private void meteAB(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException();
        if (raiz == null)
            raiz = ultimoAgregado = new VerticeRn(elemento);
        else
            meteAB(raiz, elemento);
        elementos++;
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

    private void rebalanceoMete(VerticeRn a) {

	// Caso 1 el padre es null, se pinta de negro y terminamos 
	if (!a.hayPadre()) {
	    raiz = a;
	    a.color = Color.NEGRO;
	    return;
	}

	// Caso 2, el padre era negro, termina el algoritmo
	if (a.padre.getColor() == Color.NEGRO) {
	    return;
	}

	//Caso 3 tio rojo y padre rojo, se pintan de negro, abuelo de rojo y recursion sobre el abuelo
	// Tremenda mierda de metodo, quedaria mejor de otra manera

	// Si el tio != null y el tio es rojo, se pinta al tio de y al padre de rojo y al abuelo de negro
	// despues se hace recursion sobre el abuelo

	
	VerticeRn tio = esHijoIzquierdo(a.padre())
	    ? a.padre().padre().derecho()
	    : a.padre().padre().izquierdo();
	
	if (tio != null && tio.getColor() == Color.ROJO) {
	    tio.color = Color.NEGRO;
	    a.padre().color = Color.NEGRO;
	    a.padre().padre().color = Color.ROJO;
	    rebalanceoMete(a.padre().padre());
	    return;
	}

	// Caso 4 estan cruzados, aqui entra en accion el XOR

	// Tengo entendido que si los dos cumplen en ser izq, se va al caso 5
	// Si los dos son falsos, se va al caso 5
	// Si uno es falso, caso 4
	// El XOR lo que hace es separar lo anterior 
	// A ver si soy capaza de describir como funciona esto
	VerticeRn padre = a.padre();
	if (esHijoIzquierdo(a) ^ esHijoIzquierdo(padre)) {
	    if (esHijoIzquierdo(padre))
		giroI(padre);
	    else
		giroD(padre);
	    VerticeRn aux = a;
	    a = padre;
	    padre = aux;
	}
	// Caso 5 ya estan descruzados, se pintan y se gira el abuelo
	padre.color = Color.NEGRO;
	a.padre().padre().color = Color.ROJO;
	if (esHijoIzquierdo(a))
	    giroD(a.padre().padre());
	else
	    giroI(a.padre().padre());
    }
    
}
