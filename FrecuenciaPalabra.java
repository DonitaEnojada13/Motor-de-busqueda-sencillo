import java.io.File;
public class FrecuenciaPalabra{
    private File documento;
    private int apariciones;

    public FrecuenciaPalabra(File documento, int apariciones){
        this.documento = documento;
        this.apariciones = apariciones;
    }

    public int getApariciones(){
        return this.apariciones;
    }

    public File getDocumento(){
        return this.documento;
    }
}