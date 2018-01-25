package unicauca.movil.organizadores;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import unicauca.movil.organizadores.db.UserDao;
import unicauca.movil.organizadores.models.UserRequest;

public abstract class NFCActivity extends AppCompatActivity {


    private NfcAdapter adapter;
    private PendingIntent pendingIntent;
    protected Gson gson ;

    UserDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = NfcAdapter.getDefaultAdapter(this);
        gson =  new Gson();
        pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        dao = new UserDao(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.enableForegroundDispatch(this, pendingIntent, null, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //adapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        byte[] id =  intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
        Parcelable[] rawMessages =
                intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawMessages != null) {
            NdefMessage[] messages = new NdefMessage[rawMessages.length];
            for (int i = 0; i < rawMessages.length; i++) {
                messages[i] = (NdefMessage) rawMessages[i];
            }
            String payload = new String(messages[0].getRecords()[0].getPayload());
            String data[] = payload.split("\n");

            if (data.length > 1) {
                String nombre = data[1].split(":")[1];
                String tel = data[2].split(":")[1];
                String email = data[3].split(":")[1];
                String idL = String.valueOf(getDec(id));
                UserRequest request = new UserRequest();
                request.setNombre(nombre);
                request.setEmail(email);
                request.setTel(tel);
                request.setType(getType());
                request.setIdl(idL);
                onNFCData(request);
            }
            else{
                Toast.makeText(this, "El Tag NFC se encuentra vacio", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private long getDec(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (int i = 0; i < bytes.length; ++i) {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }

    protected abstract void onNFCData(UserRequest request);
    protected abstract int getType();
}
