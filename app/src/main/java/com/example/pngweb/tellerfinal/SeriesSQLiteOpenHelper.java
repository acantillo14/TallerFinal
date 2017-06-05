package com.example.pngweb.tellerfinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class SeriesSQLiteOpenHelper extends SQLiteOpenHelper {

    private String sql = "CREATE TABLE Series(uuid text, urlfoto text, anio text, nombre text, descripcion text, protagonista text, idfoto text)";
    private static int version=4;
    public SeriesSQLiteOpenHelper(Context contexto, String name, SQLiteDatabase.CursorFactory factory){

        super(contexto, name, factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Series");
        db.execSQL(sql);
    }
}
