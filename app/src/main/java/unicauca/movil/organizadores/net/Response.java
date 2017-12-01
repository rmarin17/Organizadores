package unicauca.movil.organizadores.net;

/**
 * Created by RicardoM on 18/10/2016.
 */

public class Response {

    String msg;
    int code;
    int error;


    public Response(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public Response(int error) {
        this.error = error;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
