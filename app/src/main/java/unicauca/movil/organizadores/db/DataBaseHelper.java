package unicauca.movil.organizadores.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by RicardoM on 12/10/2016.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME="organizadores.db";
    public static int VERSION = 1;


    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(" CREATE TABLE registro (_id INTEGER PRIMARY KEY"
                +", nombre VARCHAR"
                +", tel varchar"
                +", email varchar"
                +", type varchar"
                +", idl LONG"
                +", ide LONG"
                +", actividad varchar"
                +")"
            );

        db.execSQL(" CREATE TABLE boton (_id INTEGER PRIMARY KEY AUTOINCREMENT"
                +", nombre VARCHAR"
                +")"
        );

        db.execSQL(" CREATE TABLE evento (_id INTEGER PRIMARY KEY"
                +", url VARCHAR"
                +", nombre VARCHAR"
                +")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE registro");
            onCreate(db);
    }
}
