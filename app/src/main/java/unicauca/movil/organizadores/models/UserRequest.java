package unicauca.movil.organizadores.models;

/**
 * Created by RicardoM on 18/10/2016.
 */

public class UserRequest {
    public static final int TYPE_ASIST = 0;
    public static final int TYPE_REF = 1;

    String nombre, tel, email, actividad;
    long id, idl;
    int type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static int getTypeAsist() {
        return TYPE_ASIST;
    }

    public static int getTypeRef() {
        return TYPE_REF;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getIdl() {
        return idl;
    }

    public void setIdl(long idl) {
        this.idl = idl;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }
}
