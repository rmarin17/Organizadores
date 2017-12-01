package unicauca.movil.organizadores;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import unicauca.movil.organizadores.databinding.ActivityControlRefBinding;
import unicauca.movil.organizadores.databinding.ActivityPrincipalProBinding;

public class ControlRef extends AppCompatActivity {

    ActivityControlRefBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_control_ref);
        binding.setHandler(this);
    }

    public void goAsis(){
        Intent intent = new Intent(this, AsistenciaActivity.class);
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
    }

}
