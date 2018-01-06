package unicauca.movil.organizadores.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import unicauca.movil.organizadores.models.Evento;

/**
 * Created by RicardoM on 05/01/2018.
 */

public class EventoDao {
    static final String TABLE = "evento";
    static final String C_ID = "_id";
    static final String C_URL = "url";
    static final String C_NAME = "nombre";


    SQLiteDatabase db;

    public EventoDao(Context context){
        DataBaseHelper helper = new DataBaseHelper(context);
        db = helper.getWritableDatabase();
    }
    public void insert (Evento evento){
        ContentValues cV = new ContentValues();
        cV.put(C_ID, evento.getIde());
        cV.put(C_URL, evento.getUrl());
        cV.put(C_NAME, evento.getNombre());
        db.insert(TABLE,null,cV);
    }

    public void deleteAll (){
        db.execSQL("DELETE FROM evento");
    }

    public void update (Evento evento){
        ContentValues cV = new ContentValues();
        cV.put(C_ID, evento.getIde());
        cV.put(C_URL, evento.getUrl());
        cV.put(C_NAME, evento.getNombre());
        long id = db.update(TABLE,cV,"_id = ?",new String[]{evento.getIde()+" "});
    }

    public List<Evento> getAll(){
        Cursor c = db.rawQuery("SELECT * FROM evento",null);
        return cursorToList(c);
    }

    public List<Evento> getByActivity (String actividad){
        //Cursor c = db.rawQuery("SELECT * FROM alarma WHERE fecha LIKE '%"+fecha_sistema+"%'",null);   ",null);//
        Cursor c = db.rawQuery("SELECT * FROM evento WHERE actividad='"+actividad, null);
        return cursorToList(c);
    }

    private Evento cursorToEvento (Cursor c){
        Evento evento = null;
        if (c.moveToNext()){
            evento = new Evento();
            evento.setIde(c.getLong(0));
            evento.setUrl(c.getString(1));
            evento.setNombre(c.getString(2));
        }
        return evento;
    }

    private List<Evento> cursorToList (Cursor c){
        List<Evento> data = new ArrayList<>();
        for (int i= 0; i< c.getCount();i++){
            Evento e = cursorToEvento(c);
            data.add(e);
        }
        return data;
    }

}
