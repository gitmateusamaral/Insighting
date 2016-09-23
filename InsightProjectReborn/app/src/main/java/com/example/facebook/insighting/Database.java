package com.example.facebook.insighting;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Dave.Wehner on 23/09/2016.
 */
public class Database extends SQLiteOpenHelper {

    private static final String name = "rada";
    public static String tableInsight = "InsightCard";
    public static String tableProject = "Projects";
    public Database(Context context) {
        super(context, "insighting.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createProjectSql = " CREATE TABLE "+tableProject+" (\n" +
                "                          id_project integer primary key autoincrement,\n" +
                "                          name text,\n" +
                "                          description text\n" +
                "                          )";
        String createInsightCardSql =  " CREATE TABLE " + tableInsight + " (\n" +
                "                          id_card integer primary key autoincrement,\n" +
                "                          name text,\n" +
                "                          description text,\n" +
                "                          url text,\n" +
                "                          tags text,\n" +
                "                          id_project integer\n" +
                "                          )";
        db.execSQL(createProjectSql);
        db.execSQL(createInsightCardSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + tableInsight);
        db.execSQL("DROP TABLE IF EXISTS" + tableProject
        );
        onCreate(db);
    }

}
