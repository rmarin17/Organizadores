package unicauca.movil.organizadores.net;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import unicauca.movil.organizadores.R;
import unicauca.movil.organizadores.db.EventoDao;
import unicauca.movil.organizadores.models.Evento;
import unicauca.movil.organizadores.util.L;

/**
 * Created by RicardoM on 18/10/2016.
 */

public abstract class HttpApi implements HttpAsyncTask.OnResponseListener {

    protected Context context;
    protected String urlBase;
    protected Gson gson;

    public HttpApi(Context context) {
        this.context = context;
        urlBase = L.urlAll;
        gson = new Gson();
    }

    protected boolean validateError(Response response){
        int error = response.getError();
        if(error == HttpError.NO_ERROR){
            int code = response.getCode();
            if(code == 200){
                return true;
            }else if(code == 404){
                Toast.makeText(context, R.string.http_error_404, Toast.LENGTH_SHORT).show();
                return  false;
            }else
                Toast.makeText(context, R.string.http_error_server, Toast.LENGTH_SHORT).show();
            return false;
        }else if (error == HttpError.NO_INTERNET){
            Toast.makeText(context, R.string.http_error_internet, Toast.LENGTH_SHORT).show();
            return false;
        }else if(error == HttpError.TIMEOUT){
            Toast.makeText(context, R.string.http_error_timeout, Toast.LENGTH_SHORT).show();
            return false;
        }else{
            Toast.makeText(context, R.string.http_error_server, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    protected HttpAsyncTask makeTask (int request,int method){
        return new HttpAsyncTask(context,request, method, this);
    }
}