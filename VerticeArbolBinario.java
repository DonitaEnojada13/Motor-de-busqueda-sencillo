public interface VerticeArbolBinario<T> {
    public boolean hayPadre();
    public boolean hayIzquierdo();
    public boolean hayDerecho();
    public VerticeArbolBinario<T> padre();
    public VerticeArbolBinario<T> izquierdo();
    public VerticeArbolBinario<T> derecho();
    public int altura();
    public int profundidad();
    public T get();
}
