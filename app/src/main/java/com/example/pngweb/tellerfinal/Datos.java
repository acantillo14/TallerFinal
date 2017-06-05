package com.example.pngweb.tellerfinal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;



public class Datos {
       public static ArrayList<Series> traerSeries(Context contexto){
        ArrayList<Series> series = new ArrayList<>();

           SQLiteDatabase db;
           String sql, uuid,urlfoto, anio, nombre, descripcion,protagonista, idfoto;
           Series s;
           //Abrir conexión de lectura
           SeriesSQLiteOpenHelper aux = new SeriesSQLiteOpenHelper(contexto,"DBSeries",null);
           db = aux.getReadableDatabase();

           //Cursor
           sql ="select * from Series";
           Cursor c = db.rawQuery(sql,null);

           //Recorrido del cursor
           if(c.moveToFirst()){
               do{
                   uuid = c.getString(0);
                   urlfoto=c.getString(1);
                   anio = c.getString(2);
                   nombre =c.getString(3);
                   descripcion = c.getString(4);
                   protagonista = c.getString(5);
                   idfoto = c.getColumnName(6);

                   s = new Series(uuid,urlfoto,anio,nombre,descripcion,protagonista,idfoto);
                   series.add(s);

               }while (c.moveToNext());
           }

           db.close();

           return series;



    }


}
