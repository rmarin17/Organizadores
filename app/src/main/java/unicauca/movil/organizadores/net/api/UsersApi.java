package unicauca.movil.organizadores.net.api;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import unicauca.movil.organizadores.models.Boton;
import unicauca.movil.organizadores.models.UserRequest;
import unicauca.movil.organizadores.net.HttpApi;
import unicauca.movil.organizadores.net.HttpAsyncTask;
import unicauca.movil.organizadores.net.Response;
import unicauca.movil.organizadores.util.L;

/**
 * Created by RicardoM on 18/10/2017.
 */

public class UsersApi extends HttpApi {

    private  static final int REQUEST_REG=0;
    private  static final int REQUEST_ACTIVIDADES=1;
    private  static final int REQUEST_USUARIOS=2;

    //region Callback
    public interface OnRegListener{
        void onReg(boolean success, UserRequest usr);
    }

    public interface OnActivListener{
        void onActiv(List<Boton> botones);
    }

    public interface OnUsuariosListener{
        void onUsuarios(List<UserRequest> usuarios);
    }

    OnRegListener onRegListener;
    OnUsuariosListener onUsuariosListener;
    OnActivListener onActivListener;
    //endregion


    public UsersApi(Context context) {
        super(context);
    }

    //region Registrar Usuarios
    public void registrar(UserRequest usuario, OnRegListener onRegListener){
        this.onRegListener = onRegListener;
        HttpAsyncTask task = makeTask(REQUEST_REG, HttpAsyncTask.METHOD_POST);
        String json = gson.toJson(usuario);
        //String url = urlAll;
        //String url = "http://192.168.0.106:80/asistencia/public/index.php/ref";
        task.execute(L.urlAll, json);
    }

    private void processReg(Response response) {
        if(validateError(response)){
            try {
                JSONObject json = new JSONObject(response.getMsg());
                boolean success = json.getBoolean("success");
                if(success){
                    JSONObject usuario = json.getJSONObject("obj");
                    onRegListener.onReg(true, gson.fromJson(usuario.toString(), UserRequest.class));
                }else{
                    onRegListener.onReg(false, null);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            onRegListener.onReg(false, null);
        }
    }
    //endregion

    //region Obtener Usuarios
    public void getUsuarios(OnUsuariosListener onUsuariosListener){
        this.onUsuariosListener = onUsuariosListener;
        HttpAsyncTask task = makeTask(REQUEST_USUARIOS, HttpAsyncTask.METHOD_GET);
        //String url = "http://192.168.0.106:80/asistencia/public/index.php/ref";
        //String url = urlAll;
        task.execute(L.urlAll+"?ide="+L.ideAll);
    }

    private void processUsuarios(Response response) {
        List<UserRequest> data;
        if(validateError(response)){
            Type type = new TypeToken<List<UserRequest>>(){}.getType();
            data = gson.fromJson(response.getMsg(),type);
        }else{
            data =  new ArrayList<>();
        }
        onUsuariosListener.onUsuarios(data);
    }
    //endregion

    //region Obtener Actividades
    public void getActividades(OnActivListener onActivListener){
        this.onActivListener = onActivListener;
        HttpAsyncTask task = makeTask(REQUEST_ACTIVIDADES, HttpAsyncTask.METHOD_GET);
        //String url = "http://192.168.0.106:80/asistencia/public/index.php/ref";
        //String url = urlAll;
        task.execute(L.urlAll+"/actividades?ide="+L.ideAll);
    }

    private void processActividades(Response response) {
        List<Boton> data;
        if(validateError(response)){
            Type type = new TypeToken<List<Boton>>(){}.getType();
            data = gson.fromJson(response.getMsg(),type);
        }else{
            data =  new ArrayList<>();
        }
        onActivListener.onActiv(data);
    }
    //endregion

    @Override
    public void onResponse(int request, Response response) {
        switch (request){
            case REQUEST_REG:
                processReg(response);
                break;
            case REQUEST_USUARIOS:
                processUsuarios(response);
                break;
            case REQUEST_ACTIVIDADES:
                processActividades(response);
                break;
        }
    }
}
