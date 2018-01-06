package unicauca.movil.organizadores.models;

/**
 * Created by RicardoM on 05/01/2018.
 */

public class Evento {
    long ide;
    String url,nombre;

    public long getIde() {
        return ide;
    }

    public void setIde(long ide) {
        this.ide = ide;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
