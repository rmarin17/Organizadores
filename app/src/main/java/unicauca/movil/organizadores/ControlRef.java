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
import unicauca.movil.organizadores.models.Boton;
import unicauca.movil.organizadores.util.L;

public class ControlRef extends AppCompatActivity implements ButtonAdapter.OnButtonListener {

    ActivityControlRefBinding binding;

    ButtonAdapter adapter;

    //ButtonDao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_control_ref);
        binding.setHandler(this);

        L.bdata = new ArrayList<>();
        adapter = new ButtonAdapter(getLayoutInflater(), L.bdata,this);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

        loadData();

    }


    public void loadData() {

        Boton b = new Boton();
        b.setId(1);
        b.setNombre("Refrigerio 1");

        Boton b2 = new Boton();
        b2.setId(2);
        b2.setNombre("Refrigerio 2");

        L.bdata.add(b);
        L.bdata.add(b2);

        adapter.notifyDataSetChanged();

    }

    //region Bad Buttons
    /*public void goAsis(){
        Intent intent = new Intent(this, Labor.class);
        startActivity(intent);
    }

    public void goRef1(){
        Intent intent = new Intent(this, RefrigerioActivity.class);
        startActivity(intent);
    }

    public void goRef2(){
        Intent intent = new Intent(this, Refrigerio2.class);
        startActivity(intent);
    }

    public void goRef3(){
        Intent intent = new Intent(this, Refrigerio3.class);
        startActivity(intent);
    }

    public void goRef4(){
        Intent intent = new Intent(this, Refrigerio4.class);
        startActivity(intent);
    }

    public void goRef5(){
        Intent intent = new Intent(this, Refrigerio5.class);
        startActivity(intent);
    }

    public void goAlmu(){
        Intent intent = new Intent(this, Almuerzo.class);
        startActivity(intent);
    }*/
    //endregion

    @Override
    public void onButton(int position) {

        Intent intent = new Intent(this, Labor.class);
        intent.putExtra("pos", position);
        startActivity(intent);

    }
}
