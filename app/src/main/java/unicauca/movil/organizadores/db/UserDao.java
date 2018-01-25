package unicauca.movil.organizadores.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import unicauca.movil.organizadores.models.UserRequest;

/**
 * Created by RicardoM on 28/03/2017.
 */

public class UserDao {

    static final String TABLE = "registro";
    static final String C_NAME = "nombre";
    static final String C_PHONE = "tel";
    static final String C_EMAIL = "email";
    static final String C_IDL = "idl";
    static final String C_IDE = "ide";
    static final String C_ACTIVITY = "actividad";

    static final String C_TYPE = "type";
    SQLiteDatabase db;

    public UserDao(Context context){
        DataBaseHelper helper = new DataBaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insert (UserRequest user){
        ContentValues cV = new ContentValues();
        cV.put(C_NAME, user.getNombre());
        cV.put(C_PHONE, user.getTel());
        cV.put(C_EMAIL, user.getEmail());
        cV.put(C_TYPE, user.getType());
        cV.put(C_IDL, user.getIdl());
        cV.put(C_IDE, user.getIde());
        cV.put(C_ACTIVITY, user.getActividad());
        long id = db.insert(TABLE,null,cV);
        user.setId(id);
    }

    public void update (UserRequest user){

        ContentValues cV = new ContentValues();

        cV.put(C_NAME, user.getNombre());
        cV.put(C_PHONE, user.getTel());
        cV.put(C_EMAIL, user.getEmail());
        cV.put(C_TYPE, user.getType());
        cV.put(C_IDL, user.getIdl());
        cV.put(C_IDE, user.getIde());
        cV.put(C_ACTIVITY, user.getActividad());
        long id = db.update(TABLE,cV,"_id = ?",new String[]{user.getId()+" "});
    }

    public void delete (long id){
        db.delete(TABLE,"_id = "+id, null);
    }

    public void deleteAll (){
        db.execSQL("DELETE FROM registro");
    }

    public  UserRequest getByid (long id){

        Cursor c = db.rawQuery("SELECT * FROM alarma WHERE _id="+id,null);
        return cursorToMensaje(c);

    }

    public List<UserRequest> getAll(){
        Cursor c = db.rawQuery("SELECT * FROM registro",null);
        return cursorToList(c);
    }

    public List<UserRequest> getByActivity (String actividad){
        //Cursor c = db.rawQuery("SELECT * FROM alarma WHERE fecha LIKE '%"+fecha_sistema+"%'",null);   ",null);//
        Cursor c = db.rawQuery("SELECT * FROM registro WHERE actividad='"+actividad+"'", null);
        return cursorToList(c);
    }

    private UserRequest cursorToMensaje (Cursor c){

        UserRequest user = null;

        if (c.moveToNext()){
            user = new UserRequest();
            user.setId(c.getLong(0));
            user.setNombre(c.getString(1));
            user.setTel(c.getString(2));
            user.setEmail(c.getString(3));
            user.setType(c.getInt(4));
            user.setIdl(c.getString(5));
            user.setIde(c.getLong(6));
            user.setActividad(c.getString(7));
        }
        return user;
    }

    private List<UserRequest> cursorToList (Cursor c){

        List<UserRequest> data = new ArrayList<>();

        for (int i= 0; i< c.getCount();i++){
            UserRequest u = cursorToMensaje(c);
            data.add(u);
        }

        return data;
    }

}
