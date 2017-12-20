package unicauca.movil.organizadores;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import unicauca.movil.organizadores.adapters.ButtonAdapter;
import unicauca.movil.organizadores.databinding.ActivityControlAsisBinding;
import unicauca.movil.organizadores.db.BotonDao;
import unicauca.movil.organizadores.models.Boton;
import unicauca.movil.organizadores.util.L;

public class ControlAsis extends AppCompatActivity implements ButtonAdapter.OnButtonListener {

    ActivityControlAsisBinding binding;

    ButtonAdapter adapter;

    BotonDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_control_asis);
        binding.setHandler(this);

        dao = new BotonDao(this);
        L.bdata = new ArrayList<>();
        adapter = new ButtonAdapter(getLayoutInflater(), L.bdata,this);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

        loadData();

    }

    public void loadData() {

        List<Boton> list = dao.getAll();

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
}
