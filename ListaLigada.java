import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaLigada<T> implements Iterable<T>{

    private class Nodo{
        public Nodo siguiente;
        public T elemento;

        public Nodo(T elemento){
            if(elemento == null )
		throw new NullPointerException ("Error");
            this.elemento = elemento;
        }
        //Métodos get y set del nodo
	
        public Nodo getSiguiente(){
            return siguiente;
        }

        public void setSiguiente(Nodo siguiente){
            this.siguiente = siguiente;
        }

        public T getElemento(){
            return elemento;
        }

        public void setElemento(T elemento){
            this.elemento = elemento;
        }
    }

    private Nodo cabeza, rabo;
    private int size;

    @Override
    public Iterator<T> iterator(){
        return new IteradorLista();
    }

    public void agregarFinal(T elemento){
        if(elemento == null)
	    throw new NullPointerException("No hay elemento a añadir");
        Nodo nodoAgregar = new Nodo(elemento);
      
        if(esVacia()){
	    cabeza = rabo = nodoAgregar;
        } else {
	rabo.setSiguiente(nodoAgregar);
	rabo = nodoAgregar;
        }
	size++;
    }
    
    private boolean esVacia(){
        return this.cabeza == null && this.rabo == null;
    }
    
    public class IteradorLista implements Iterator<T>{
        private Nodo actual;
	
        public IteradorLista(){
            this.actual = cabeza;
        }
	
        @Override
        public boolean hasNext(){
            return actual != null;
        }

        @Override
        public T next(){
	    if(!hasNext()) throw new NoSuchElementException("No hay elementos en la lista");
            T dato = actual.getElemento();
            actual = actual.getSiguiente();
            return dato;
        }
    }
}
