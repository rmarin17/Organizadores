package unicauca.movil.organizadores;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import unicauca.movil.organizadores.databinding.ActivityPrincipalProBinding;
import unicauca.movil.organizadores.db.BotonDao;
import unicauca.movil.organizadores.db.EventoDao;
import unicauca.movil.organizadores.db.UserDao;
import unicauca.movil.organizadores.models.Boton;
import unicauca.movil.organizadores.models.Evento;
import unicauca.movil.organizadores.models.UserRequest;
import unicauca.movil.organizadores.net.api.UsersApi;
import unicauca.movil.organizadores.util.L;

public class PrincipalPro extends AppCompatActivity implements DialogInterface.OnClickListener {

    ActivityPrincipalProBinding binding;
    BotonDao bdao;
    UserDao udao;
    EventoDao edao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_principal_pro);
        binding.setHandler(this);

        bdao = new BotonDao(this);
        udao = new UserDao(this);
        edao = new EventoDao(this);


        List<Evento> elist = edao.getAll();
        L.urlAll = elist.get(0).getUrl();
        L.ideAll = elist.get(0).getIde();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_prin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.salir:
                generateAlert();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void generateAlert(){
        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle(R.string.alert_title_event)
                .setIcon(R.drawable.ic_warning)
                .setMessage(R.string.alert_msg_event)
                .setPositiveButton(R.string.ok,this)
                .setNegativeButton(R.string.cancel, this)
                .create();
        alert.show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if( i == DialogInterface.BUTTON_POSITIVE) {
            udao.deleteAll();
            bdao.deleteAll();
            edao.deleteAll();
            Intent inten = new Intent(this, Principal.class);
            inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(inten);
        }
        if( i == DialogInterface.BUTTON_NEGATIVE) {

        }
    }
}
