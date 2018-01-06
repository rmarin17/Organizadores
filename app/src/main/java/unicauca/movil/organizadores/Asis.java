package unicauca.movil.organizadores;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import unicauca.movil.organizadores.adapters.UserAdapterPro;
import unicauca.movil.organizadores.databinding.ActivityAsisBinding;
import unicauca.movil.organizadores.db.UserDao;
import unicauca.movil.organizadores.models.Boton;
import unicauca.movil.organizadores.models.UserRequest;
import unicauca.movil.organizadores.util.L;

public class Asis extends AppCompatActivity implements UserAdapterPro.OnUserListener {

    ActivityAsisBinding binding;

    UserAdapterPro adapter;
    UserDao dao;

    String actividad;
    String actividad_replace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_asis);



        L.data = new ArrayList<>();
        dao = new UserDao(this);

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
        List<UserRequest> list = dao.getByActivity(actividad_replace);
        binding.canti.setText(""+list.size());
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
    public void onUser(View v) {

    }
}
