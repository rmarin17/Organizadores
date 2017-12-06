package unicauca.movil.organizadores;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import unicauca.movil.organizadores.databinding.ActivityPrincipalProBinding;

public class PrincipalPro extends AppCompatActivity {

    ActivityPrincipalProBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_principal_pro);
        binding.setHandler(this);
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
