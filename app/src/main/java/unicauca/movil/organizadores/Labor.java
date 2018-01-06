package unicauca.movil.organizadores;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import unicauca.movil.organizadores.adapters.UserAdapterPro;
import unicauca.movil.organizadores.databinding.ActivityLaborBinding;
import unicauca.movil.organizadores.db.EventoDao;
import unicauca.movil.organizadores.db.UserDao;
import unicauca.movil.organizadores.models.Boton;
import unicauca.movil.organizadores.models.Evento;
import unicauca.movil.organizadores.models.UserRequest;
import unicauca.movil.organizadores.net.HttpAsyncTask;
import unicauca.movil.organizadores.net.Response;
import unicauca.movil.organizadores.util.L;

public class Labor extends NFCActivity implements HttpAsyncTask.OnResponseListener, UserAdapterPro.OnUserListener, DialogInterface.OnClickListener {

    ActivityLaborBinding binding;

    UserAdapterPro adapter;
    EventoDao edao;

    String actividad;
    String actividad_replace;

    List<UserRequest> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_labor);
        data = new ArrayList<UserRequest>();
        L.data = new ArrayList<>();
        edao = new EventoDao(this);
        int pos = getIntent().getExtras().getInt("pos");
        Boton boton = L.bdata.get(pos);
        actividad = boton.getNombre();
        actividad_replace = actividad.replace(" ", "_");
        binding.setActi(boton);
        adapter = new UserAdapterPro(getLayoutInflater(), L.data, this);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        loadData();
    }

    public void loadData() {
        L.data.clear();
        List<UserRequest> list = dao.getByActivity(actividad_replace);
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
        request.setActividad(actividad_replace);
        Long codigo = request.getIdl();
        int alarma = 0;
        List<UserRequest> list = dao.getByActivity(actividad);
        List<Evento> elist = edao.getAll();
        if(list.size() > 0 ) {
            for (UserRequest u : list) {
                data.add(u);
                if (codigo == u.getIdl()){
                    generateAlert();
                    alarma = 1;
                }
            }
        }
        if (alarma == 0){
            dao.insert(request);
            String url = elist.get(0).getUrl();
            String json = gson.toJson(request);
            //String url = "http://192.168.0.107:8080/asistencia/public/index.php/ref";
            UserRequest request1 = gson.fromJson(json, UserRequest.class);
            HttpAsyncTask task = new HttpAsyncTask(this, 101, HttpAsyncTask.METHOD_POST, this);
            task.execute(url, json);
            Toast.makeText(this, R.string.correcto, Toast.LENGTH_SHORT).show();
            loadData();
        }
    }

    public void generateAlert() {
        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle(R.string.msg_alert)
                .setIcon(R.drawable.ic_warning)
                .setMessage(R.string.alert_msg_validar)
                .setPositiveButton(R.string.ok,this)
                .create();
        alert.show();
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

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
    }
}
