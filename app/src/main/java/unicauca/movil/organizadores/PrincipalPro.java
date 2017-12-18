package unicauca.movil.organizadores;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

        Boton b = new Boton();
        b.setNombre("Refrigerio_1");

        Boton b1 = new Boton();
        b1.setNombre("Refrigerio_2");

        dao.insert(b);
        dao.insert(b1);



    }


    public void goToMain(){
        Intent intent = new Intent(this, ControlRef.class);
        startActivity(intent);
    }

    public void goToTags(){
        Intent intent = new Intent(this, ControlNfc.class);
        startActivity(intent);
    }
}
