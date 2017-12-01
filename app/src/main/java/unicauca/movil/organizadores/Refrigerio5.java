package unicauca.movil.organizadores;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import unicauca.movil.organizadores.models.UserRequest;
import unicauca.movil.organizadores.net.HttpAsyncTask;
import unicauca.movil.organizadores.net.Response;

public class Refrigerio5 extends NFCActivity implements HttpAsyncTask.OnResponseListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refrigerio5);
    }

    @Override
    protected void onNFCData(UserRequest request) {

        String json = gson.toJson(request);
        String url = "http://192.168.43.232:8080/ref5/public/index.php/ref";
        UserRequest request1 = gson.fromJson(json, UserRequest.class);

        HttpAsyncTask task = new HttpAsyncTask(this, 101, HttpAsyncTask.METHOD_POST, this);
        task.execute(url, json);

        Toast.makeText(this, R.string.correcto, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int getType() {
        return UserRequest.TYPE_ASIST;
    }

    @Override
    public void onResponse(int request, Response response) {
        Log.i("","");
    }
}
