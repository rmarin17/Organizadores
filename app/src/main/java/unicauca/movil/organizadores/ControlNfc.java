package unicauca.movil.organizadores;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import unicauca.movil.organizadores.databinding.ActivityControlNfcBinding;

public class ControlNfc extends AppCompatActivity {

    ActivityControlNfcBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_control_nfc);
        binding.setHandler(this);
    }

    public void goToWrite(){
        Intent intent = new Intent(this, Write.class);
        startActivity(intent);
    }

    public void goToRead(){
        Intent intent = new Intent(this, Write.class);
        startActivity(intent);
    }

    public void goToDelete(){
        Intent intent = new Intent(this, Write.class);
        startActivity(intent);
    }
}
