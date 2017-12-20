package unicauca.movil.organizadores;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import unicauca.movil.organizadores.databinding.ActivityPrincipalProBinding;
import unicauca.movil.organizadores.db.BotonDao;
import unicauca.movil.organizadores.models.Boton;

public class PrincipalPro extends AppCompatActivity {

    ActivityPrincipalProBinding binding;

    BotonDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_principal_pro);
        binding.setHandler(this);


        dao = new BotonDao(this);

        List<Boton> list = dao.getAll();
        if (list.size() == 0){
            Boton b = new Boton();
            b.setNombre("Refrigerio 1");

            Boton b1 = new Boton();
            b1.setNombre("Refrigerio 2");

            dao.insert(b);
            dao.insert(b1);
        }

    }


    public void goToMain(){
        Intent intent = new Intent(this, ControlRef.class);
        startActivity(intent);
    }

    public void goToTags(){
        Intent intent = new Intent(this, ControlNfc.class);
        startActivity(intent);
    }

    public void goToRegis(){
        Intent intent = new Intent(this, ControlAsis.class);
        startActivity(intent);
    }
}
