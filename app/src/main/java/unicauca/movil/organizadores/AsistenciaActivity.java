package unicauca.movil.organizadores;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import unicauca.movil.organizadores.adapters.UserAdapterPro;
import unicauca.movil.organizadores.databinding.ActivityAsistenciaBinding;
import unicauca.movil.organizadores.db.UserDao;
import unicauca.movil.organizadores.models.UserRequest;
import unicauca.movil.organizadores.net.HttpApi;
import unicauca.movil.organizadores.net.HttpAsyncTask;
import unicauca.movil.organizadores.net.Response;
import unicauca.movil.organizadores.util.L;

public class AsistenciaActivity extends NFCActivity implements HttpAsyncTask.OnResponseListener, UserAdapterPro.OnUserListener {

    ActivityAsistenciaBinding binding;

    UserAdapterPro adapter;
    UserDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_asistencia);

        binding.setHandler(this);

        L.data = new ArrayList<>();
        dao = new UserDao(this);


        adapter = new UserAdapterPro(getLayoutInflater(), L.data, this);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

        loadData();
    }

    public void loadData() {

        List<UserRequest> list = dao.getAll();

        if(list.size() > 0 ) {
            for (UserRequest u : list) {
                L.data.add(u);
            }
            adapter.notifyDataSetChanged();
        }
        else {
            Toast.makeText(this, R.string.empy, Toast.LENGTH_LONG).show();
        }

    }


    @Override
    protected void onNFCData(UserRequest request) {


        dao.insert(request);

        String json = gson.toJson(request);
        String url = "http://192.168.43.232:8080/tet/public/index.php/ref";
        UserRequest request1 = gson.fromJson(json, UserRequest.class);

        HttpAsyncTask task = new HttpAsyncTask(this, 101, HttpAsyncTask.METHOD_POST, this);
        task.execute(url, json);

        Toast.makeText(this, R.string.correcto, Toast.LENGTH_SHORT).show();

        loadData();

    }

    @Override
    protected int getType() {
        return UserRequest.TYPE_ASIST;
    }


    @Override
    public void onResponse(int request, Response response) {
        Log.i("","");
    }

    @Override
    public void onUser(View v) {

    }
}
