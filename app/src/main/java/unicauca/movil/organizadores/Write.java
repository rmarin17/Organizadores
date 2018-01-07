package unicauca.movil.organizadores;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Toast;

import java.io.IOException;
import java.util.regex.Pattern;

import unicauca.movil.organizadores.databinding.ActivityWriteBinding;

public class Write extends AppCompatActivity {

    ActivityWriteBinding binding;

    String name, tel ,email;

    boolean mWriteMode = false;
    private NfcAdapter mNfcAdapter;
    private PendingIntent mNfcPendingIntent;

    int cont = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_write);
        binding.setHandler(this);


        name =  binding.nombre.getEditText().getText().toString();
        tel =  binding.telefono.getEditText().getText().toString();
        email =  binding.correo.getEditText().getText().toString();

    }

    public void write(){

        String nombre = binding.nombre.getEditText().getText().toString();
        String telefono =binding.telefono.getEditText().getText().toString();
        String correo = binding.correo.getEditText().getText().toString();

        boolean a = esNombreValido(nombre);
        boolean b = esTelefonoValido(telefono);
        boolean c = esCorreoValido(correo);

        if (a && b && c) {
            mNfcAdapter = NfcAdapter.getDefaultAdapter(Write.this);
            mNfcPendingIntent = PendingIntent.getActivity(Write.this, 0,
                    new Intent(Write.this, Write.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

            enableTagWriteMode();
            generateAlert();
        }

        else {
            Toast.makeText(this, "Por favor completa los campos", Toast.LENGTH_LONG)
                    .show();
        }


    }

    private boolean esNombreValido(String nombre) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(nombre).matches() || nombre.length() > 30) {
            binding.nombre.setError("Nombre inválido");
            return false;
        } else {
            binding.nombre.setError(null);
        }

        return true;
    }

    private boolean esTelefonoValido(String telefono) {
        if (!Patterns.PHONE.matcher(telefono).matches()) {
            binding.telefono.setError("Teléfono inválido");
            return false;
        } else {
            binding.telefono.setError(null);
        }

        return true;
    }

    private boolean esCorreoValido(String correo) {
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            binding.correo.setError("Correo electrónico inválido");
            return false;
        } else {
            binding.correo.setError(null);
        }

        return true;
    }

    public void generateAlert(){

        AlertDialog alert = new AlertDialog.Builder(Write.this)
                .setTitle(R.string.alert_title_nfc)
                .setIcon(R.drawable.nfc)
                .setMessage(R.string.alert_msg_nfc)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        disableTagWriteMode();
                    }

                }).create();
                alert.show();

    }


    private void enableTagWriteMode() {
        mWriteMode = true;
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter[] mWriteTagFilters = new IntentFilter[] { tagDetected };
        mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mWriteTagFilters, null);
    }

    private void disableTagWriteMode() {
        mWriteMode = false;
        mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // Tag writing mode
        if (mWriteMode && NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            NdefRecord record = NdefRecord.createUri( "Contacto\nNombre:"+binding.nombre.getEditText().getText().toString()
                    +"\nTEL:"+binding.telefono.getEditText().getText().toString()
                    +"\nEMAIL:"+ binding.correo.getEditText().getText().toString());
            NdefMessage message = new NdefMessage(new NdefRecord[] { record });
            if (writeTag(message, detectedTag)) {
                Toast.makeText(this, R.string.action_complete, Toast.LENGTH_LONG)
                        .show();
                binding.nombre.getEditText().setText("");
                binding.telefono.getEditText().setText("");
                binding.correo.getEditText().setText("");

            }
        }
    }

    /*
    * Writes an NdefMessage to a nfc tag
    */
    public boolean writeTag(NdefMessage message, Tag tag) {
        int size = message.toByteArray().length;
        try {
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                ndef.connect();
                if (!ndef.isWritable()) {
                    Toast.makeText(getApplicationContext(),
                            "Error: tag not writable",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (ndef.getMaxSize() < size) {
                    Toast.makeText(getApplicationContext(),
                            "Error: tag too small",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
                ndef.writeNdefMessage(message);
                return true;
            } else {
                NdefFormatable format = NdefFormatable.get(tag);
                if (format != null) {
                    try {
                        format.connect();
                        format.format(message);
                        return true;
                    } catch (IOException e) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
    }
}
