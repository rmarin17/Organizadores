package unicauca.movil.organizadores;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import unicauca.movil.organizadores.databinding.ActivityCargaDatosBinding;
import unicauca.movil.organizadores.db.BotonDao;
import unicauca.movil.organizadores.models.Boton;

public class CargaDatos extends AppCompatActivity implements DialogInterface.OnClickListener {


    private static final int READ_REQUEST_CODE = 42;

    Uri archivo;
    ActivityCargaDatosBinding binding;
    Boton b;
    BotonDao bdao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_carga_datos);

        binding.setHandler(this);


        b = new Boton();

        bdao = new BotonDao(this);

        performFileSearch();
    }

    public void readXmlPullParser() {
        XmlPullParserFactory factory;
        InputStream fis = null;
        String nombre = null;
        String apellido = null;
        String empresa = null;
        String tipo = null;
        try {
            StringBuilder sb = new StringBuilder();
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            InputStream inputStream = getContentResolver().openInputStream(archivo);

            parser.setInput(inputStream, null);

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG) {
                    String condi = parser.getName();

                    switch (condi){

                        //region Actividades
                        case "ida":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String idp = readText(parser);
                            b.setId(Integer.parseInt(idp));
                            break;

                        case "nombre":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            nombre = readText(parser);
                            b.setNombre(nombre);
                            bdao.insert(b);
                            break;


                    }
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }



    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("*/*");

        startActivityForResult(intent, READ_REQUEST_CODE);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Toast.makeText(this, "ARCHIVO: " +uri.toString(), Toast.LENGTH_LONG).show();
                archivo = uri;

            }

        }

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_CANCELED) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().

            Intent inten = new Intent(this, Principal.class);
            startActivity(inten);
            finish();
        }
    }


    public void generateAlert(){

        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle(R.string.alert_title)
                .setIcon(R.drawable.ic_check)
                .setMessage(R.string.alert_msg)
                .setPositiveButton(R.string.ok,this)
                .setNegativeButton(R.string.cancel, this)
                .create();
        alert.show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

        if( i == DialogInterface.BUTTON_POSITIVE) {

            readXmlPullParser();

            List<Boton> listb = bdao.getAll();
            if (listb.size() > 0 ){

                Intent intent = new Intent(this, PrincipalPro.class);
                startActivity(intent);
                finish();
            }

            else{
                Toast.makeText(this, R.string.msj_carga, Toast.LENGTH_LONG).show();
            }

        }

        if( i == DialogInterface.BUTTON_NEGATIVE) {

        }

    }
}
