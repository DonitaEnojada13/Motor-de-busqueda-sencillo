public class Peso {
    /* Aqui va todo lo relacionado con la parte de los pesos y las frecuencias. Mas que nada
       aqui tendremos todo lo relacionado con la parte matematica del proyecto.
     */
    
    public double calcularTF(int ocurrencias) {
	if (ocurrencias > 0)
	    return log2((double) ocurrencias) + 1;
	return 0.0;
    }

    public double calcularIDF(int totalDocs, int relativoDocs) {
	if (totalDocs > 0 && relativoDocs > 0)
	    return log2((double)(totalDocs + 1)/relativoDocs);
	return 0.0;
    }
    public double calcularPeso(double tf, double idf) {
	return tf*idf;
    }
    
    private double log2(double num) {
	if (num <= 0)
	    throw new IllegalArgumentException("Aqui no trabajamos con infinitos");
	return Math.log(num)/Math.log(2);
    }

    
}
