package com.example.facebook.insighting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Dave.Wehner on 23/09/2016.
 */
public class DatabaseController {
    private SQLiteDatabase db;
    private Database myDatabase;

    public DatabaseController(Context context){
        myDatabase = new Database(context);
    }
    public String insertData( String name, String description) {
        ContentValues valores;
        long resultado;

        db = myDatabase.getWritableDatabase();
        valores = new ContentValues();
        valores.put("name", name);
        valores.put("description", description);

        resultado = db.insert(Database.tableProject, null, valores);
        db.close();

        if (resultado == -1){
            return "Erro ao inserir registro";
        }
        else {
            return "Registro Inserido com sucesso";
        }
    }

    public String readData(){
        Cursor cursor;
        String[] campos =  {"name","description"};
        db = myDatabase.getReadableDatabase();
        cursor = db.query(Database.tableProject, campos, null, null, null, null, null, null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor.getString(0);
    }

}
