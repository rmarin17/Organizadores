package unicauca.movil.organizadores;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import unicauca.movil.organizadores.databinding.ActivityPrincipalBinding;
import unicauca.movil.organizadores.db.BotonDao;
import unicauca.movil.organizadores.db.EventoDao;
import unicauca.movil.organizadores.db.UserDao;
import unicauca.movil.organizadores.models.Boton;
import unicauca.movil.organizadores.models.Evento;
import unicauca.movil.organizadores.models.UserRequest;

public class Principal extends AppCompatActivity {

    ActivityPrincipalBinding binding;
    BotonDao bdao;
    UserDao udao;
    EventoDao edao;
    //int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 10001;
    private static final int READ_REQUEST_CODE = 42;
    //private List<String> fileList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_principal);
        binding.setHandler(this);

        bdao = new BotonDao(this);
        udao = new UserDao(this);
        edao = new EventoDao(this);

        List<Boton> listb = bdao.getAll();
        List<Evento> liste = edao.getAll();
        if (listb.size() > 0 && liste.size() >0){
            Intent intent = new Intent(this, PrincipalPro.class);
            startActivity(intent);
            finish();
        }
    }

    public void goToExplorer(){
        Intent carga = new Intent(Principal.this, CargaDatos.class);
        startActivity(carga);
        finish();
    }

}
