package unicauca.movil.organizadores;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import unicauca.movil.organizadores.databinding.ActivityReadBinding;
import unicauca.movil.organizadores.models.UserRequest;

public class Read extends NFCActivity implements DialogInterface.OnClickListener{

    ActivityReadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_read);
        binding.setHandler(this);

        generateAlert();
    }

    @Override
    protected void onNFCData(UserRequest request) {
        binding.nombre.setText(request.getNombre());
        binding.telefono.setText(request.getTel());
        binding.mail.setText(request.getEmail());
    }

    public void generateAlert() {

        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle(R.string.msg_read)
                .setIcon(R.drawable.ic_read)
                .setMessage(R.string.alert_msg_read)
                .setPositiveButton(R.string.ok,this)
                .create();
        alert.show();
    }

    @Override
    protected int getType() {
        return 0;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {



    }
}
