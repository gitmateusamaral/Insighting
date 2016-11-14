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

    public String insertDataInsightCard( String name, String description, String url, String tags, String id_project) {
        ContentValues valores;
        long resultado;

        db = myDatabase.getWritableDatabase();
        valores = new ContentValues();
        valores.put("name", name);
        valores.put("description", description);
        valores.put("url", url);
        valores.put("tags", tags);
        valores.put("id_project", id_project);

        resultado = db.insert(Database.tableInsight, null, valores);
        db.close();

        if (resultado == -1){
            return "Erro ao inserir registro";
        }
        else {
            return "Registro Inserido com sucesso";
        }
    }

    public Cursor getInsightCardFromId(String id){
        Cursor cursor;
        String[] campos =  {"name","description","tags","url","id_card"};
        db = myDatabase.getReadableDatabase();
        cursor = db.query(Database.tableInsight, campos, "id_card='"+ id+"'", null, null, null, null, null);
        return cursor;
    }

    public Cursor getInsightCardsFromProjects(String id){
        Cursor cursor;
        String[] campos =  {"name","url","id_card","tags"};
        db = myDatabase.getReadableDatabase();
        cursor = db.query(Database.tableInsight, campos, "id_project='"+ id+"'", null, null, null, null, null);
        return cursor;
    }

    public String updateInsightCard(String title, String description, String tags, String url, String id_card){
        ContentValues cv = new ContentValues();
        cv.put("name",title);
        cv.put("description",description);
        cv.put("url",url);
        cv.put("tags",tags);
        Integer result = db.update(Database.tableInsight, cv, "id_card="+id_card, null);
        if (result == -1){
            return "Erro ao inserir registro";
        }
        else {
            return "Registro Inserido com sucesso";
        }
    }

    public Cursor getSpecificProject(String id){
        Cursor cursor;
        String[] campos =  {"id_project","name","description"};
        db = myDatabase.getReadableDatabase();
        cursor = db.query(Database.tableProject, campos, "id_project='"+ id+"'", null, null, null, null, null);
        return cursor;
    }

    public Cursor readProjectData(){
        Cursor cursor;
        String[] campos =  {"id_project","name","description"};
        db = myDatabase.getReadableDatabase();
        cursor = db.query(Database.tableProject, campos, null, null, null, null, null, null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void deleteProjectData(String id) {
        Log.v("DeleteRow",id);
        db = myDatabase.getWritableDatabase();
        db.delete(Database.tableProject,"id_project=?",new String[]{id});
        db.delete(Database.tableInsight,"id_project=?",new String[]{id});
        db.close();
    }

}
