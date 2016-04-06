package fct.sreolid.appbiblioteca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by sreolid on 31/03/2016.
 */
public class MyDBAdapter {

    private static final String DATABASE_NAME = "dblistado.db";
    private static final String DATABASE_TABLE1 = "libro";
    private static final int DATABASE_VERSION = 1;

    private static final String FECHA = "fecha";
    private static final String LIBRO = "libr";

    private static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE1 + " (_id integer primary key autoincrement, fecha text, libr text);";
    private static final String DATABASE_DROP = "DROP TABLE IF EXISTS " + DATABASE_TABLE1 + ";";

    // Contexto de la aplicación que usa la base de datos
    private final Context context;
    // Clase SQLiteOpenHelper para crear/actualizar la base de datos
    private MyDbHelper dbHelper;
    // Instancia de la base de datos
    private SQLiteDatabase db;

    public MyDBAdapter(Context c) {
        context = c;
        dbHelper = new MyDbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        //OJO open();
    }

    public void open() {

        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            db = dbHelper.getReadableDatabase();
        }

    }

    //Método que guarda en la BBDD los libros que saca el usuario
    public void insertarLibro(String f, String l) {
        //Creamos un nuevo registro de valores a insertar
        ContentValues newValues = new ContentValues();
        //Asignamos los valores de cada campo
        newValues.put("fecha", f);
        newValues.put("libr", l);

        db.insert(DATABASE_TABLE1, null, newValues);

    }


    //Método para recuperar todos los libros
    public ArrayList<String> recuperarLibros(){
        ArrayList<String> libros = new ArrayList<String>();

        //Recuperamos la consulta en un cursor
        Cursor cursor = db.query(DATABASE_TABLE1, null, null, null, null, null, null);
        //Recorremos el cursor para ir añadiendo
        if (cursor !=null && cursor.moveToFirst()){
            do{
                libros.add(cursor.getString(1)+""+cursor.getString(2));
            }while(cursor.moveToNext());
        }

        return libros;
    }




    private static class MyDbHelper extends SQLiteOpenHelper {

        public MyDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DATABASE_DROP);

            onCreate(db);
        }


    }
}
