package unicauca.movil.organizadores;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import unicauca.movil.organizadores.adapters.UserAdapterPro;
import unicauca.movil.organizadores.databinding.ActivityLaborBinding;
import unicauca.movil.organizadores.db.UserDao;
import unicauca.movil.organizadores.models.Boton;
import unicauca.movil.organizadores.models.UserRequest;
import unicauca.movil.organizadores.net.HttpAsyncTask;
import unicauca.movil.organizadores.net.Response;
import unicauca.movil.organizadores.util.L;

public class Labor extends NFCActivity implements HttpAsyncTask.OnResponseListener, UserAdapterPro.OnUserListener {

    ActivityLaborBinding binding;

    UserAdapterPro adapter;
    UserDao dao;

    String actividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_labor);



        L.data = new ArrayList<>();
        dao = new UserDao(this);

        int pos = getIntent().getExtras().getInt("pos");
        Boton boton = L.bdata.get(pos);
        actividad = boton.getNombre();

        binding.setActi(boton);

        adapter = new UserAdapterPro(getLayoutInflater(), L.data, this);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

        loadData();

    }

    public void loadData() {
        L.data.clear();

        List<UserRequest> list = dao.getByActivity(actividad);

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

        request.setActividad(actividad);

        dao.insert(request);

        String json = gson.toJson(request);
        String url = "http://192.168.43.180:7070/ref1/public/index.php/ref";
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
