package unicauca.movil.organizadores.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import unicauca.movil.organizadores.models.Boton;
import unicauca.movil.organizadores.models.UserRequest;

/**
 * Created by RicardoM on 18/12/2017.
 */

public class BotonDao {

    static final String TABLE = "boton";
    static final String C_NAME = "nombre";

    SQLiteDatabase db;

    public BotonDao(Context context){
        DataBaseHelper helper = new DataBaseHelper(context);
        db = helper.getWritableDatabase();
    }
//algo
    public void insert (Boton boton){
        ContentValues cV = new ContentValues();
        cV.put(C_NAME, boton.getNombre());
        long id = db.insert(TABLE,null,cV);
        boton.setId(id);
    }

    public void update (Boton boton){

        ContentValues cV = new ContentValues();

        cV.put(C_NAME, boton.getNombre());
        long id = db.update(TABLE,cV,"_id = ?",new String[]{boton.getId()+" "});
    }

    public void delete (long id){
        db.delete(TABLE,"_id = "+id, null);
    }

    public  Boton getByid (long id){

        Cursor c = db.rawQuery("SELECT * FROM alarma WHERE _id="+id,null);
        return cursorToboton(c);

    }

    public List<Boton> getAll(){
        Cursor c = db.rawQuery("SELECT * FROM boton",null);
        return cursorToList(c);
    }

    public List<Boton> getByActivity (String actividad){

        //Cursor c = db.rawQuery("SELECT * FROM alarma WHERE fecha LIKE '%"+fecha_sistema+"%'",null);   ",null);//
        Cursor c = db.rawQuery("SELECT * FROM registro WHERE actividad='"+actividad, null);
        return cursorToList(c);
    }

    private Boton cursorToboton (Cursor c){

        Boton boton = null;

        if (c.moveToNext()){
            boton = new Boton();
            boton.setId(c.getLong(0));
            boton.setNombre(c.getString(1));

        }
        return boton;
    }

    private List<Boton> cursorToList (Cursor c){

        List<Boton> data = new ArrayList<>();

        for (int i= 0; i< c.getCount();i++){
            Boton b = cursorToboton(c);
            data.add(b);
        }

        return data;
    }

}
