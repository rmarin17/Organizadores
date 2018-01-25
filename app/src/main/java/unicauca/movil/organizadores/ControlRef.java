package unicauca.movil.organizadores;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import unicauca.movil.organizadores.adapters.ButtonAdapter;
import unicauca.movil.organizadores.databinding.ActivityControlRefBinding;
import unicauca.movil.organizadores.db.BotonDao;
import unicauca.movil.organizadores.db.UserDao;
import unicauca.movil.organizadores.models.Boton;
import unicauca.movil.organizadores.models.UserRequest;
import unicauca.movil.organizadores.net.api.UsersApi;
import unicauca.movil.organizadores.util.L;

public class ControlRef extends AppCompatActivity implements ButtonAdapter.OnButtonListener, UsersApi.OnActivListener, UsersApi.OnUsuariosListener {

    ActivityControlRefBinding binding;

    ButtonAdapter adapter;
    UsersApi api;
    UserDao udao;
    BotonDao bdao;
    long tamañob=0,tamañou=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_control_ref);
        binding.setHandler(this);
        udao = new UserDao(this);
        bdao = new BotonDao(this);
        api = new UsersApi(this);
        List<Boton> bList = bdao.getAll();
        tamañob = bList.size();
        List<UserRequest> ulist = udao.getAll();
        tamañou = ulist.size();
        L.bdata = new ArrayList<>();
        adapter = new ButtonAdapter(getLayoutInflater(), L.bdata,this);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        loadData();
    }


    public void loadData() {

        api.getActividades(this);
        api.getUsuarios(this);

        List<Boton> list = bdao.getAll();
        L.bdata.clear();
        if(list.size() > 0 ) {
            for (Boton b : list) {
                L.bdata.add(b);
            }
            adapter.notifyDataSetChanged();
        }
        else {
            Toast.makeText(this, R.string.empy, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onButton(int position) {

        Intent intent = new Intent(this, Labor.class);
        intent.putExtra("pos", position);
        startActivity(intent);

    }

    @Override
    public void onActiv(List<Boton> botones) {
        L.bdata.clear();
        if (tamañob!= botones.size()) {
            bdao.deleteAll();
            for(Boton b: botones){
                bdao.insert(b);
                L.bdata.add(b);
            }
        } else {
            for(Boton b: botones){
                bdao.update(b);
                L.bdata.add(b);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onUsuarios(List<UserRequest> usuarios) {
        if (tamañou!= usuarios.size()) {
            udao.deleteAll();
            for(UserRequest u: usuarios){
                udao.insert(u);
            }
        } else {
            for(UserRequest u: usuarios){
                udao.update(u);
            }
        }
    }
}
