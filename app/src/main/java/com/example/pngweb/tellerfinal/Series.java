package com.example.pngweb.tellerfinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Created by PNGWEB on 04/06/2017.
 */

public class Series {

    private String uuid;
    private String urlfoto;
    private String idfoto;
    private String anio;
    private String nombre;
    private String descripcion;
    private String protagonista;


    public Series(){

    }

    public Series(String urlfoto, String anio, String nombre, String descripcion, String protagonista, String idfoto) {
        this.uuid= UUID.randomUUID().toString();
        this.urlfoto = urlfoto;
        this.idfoto = idfoto;
        this.anio = anio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.protagonista = protagonista;
    }

    public Series(String uuid, String urlfoto, String anio, String nombre, String descripcion, String protagonista , String idfoto) {
        this.uuid = uuid;
        this.urlfoto = urlfoto;
        this.idfoto = idfoto;
        this.anio = anio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.protagonista = protagonista;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUrlfoto() {
        return urlfoto;
    }

    public void setUrlfoto(String urlfoto) {
        this.urlfoto = urlfoto;
    }

    public String getIdfoto() {
        return idfoto;
    }

    public void setIdfoto(String idfoto) {
        this.idfoto = idfoto;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getProtagonista() {
        return protagonista;
    }

    public void setProtagonista(String protagonista) {
        this.protagonista = protagonista;
    }


    public  void guardar(Context contexto){
        guardarRemoto(contexto);
    }
    public void guardarRemoto(final Context contexto){
        new AsyncTask<Void, Void, String>(){

            @Override
            protected String doInBackground(Void... params) {
                HttpURLConnection conexion = null;

                try {
                    URL url = new URL("http://107.23.88.1/guardar1.php");
                    conexion =(HttpURLConnection)url.openConnection();
                    conexion.setConnectTimeout(30000);
                    conexion.setReadTimeout(30000);

                    //Configuracion de envío de datos via POST
                    conexion.setRequestMethod("POST");
                    conexion.setDoOutput(true);
                    conexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                    //Crear consulta con los datos

                    StringBuilder builder = new StringBuilder();
                    builder.append("id");
                    builder.append("=");
                    builder.append(URLEncoder.encode(uuid,"UTF-8"));
                    builder.append("&");
                    builder.append("foto");
                    builder.append("=");
                    builder.append(URLEncoder.encode(getUrlfoto(),"UTF-8"));
                    builder.append("&");
                    builder.append("anio");
                    builder.append("=");
                    builder.append(URLEncoder.encode(anio,"UTF-8"));
                    builder.append("&");
                    builder.append("nombre");
                    builder.append("=");
                    builder.append(URLEncoder.encode(nombre,"UTF-8"));
                    builder.append("&");
                    builder.append("descripcion");
                    builder.append("=");
                    builder.append(URLEncoder.encode(descripcion,"UTF-8"));
                    builder.append("&");
                    builder.append("protagonista");
                    builder.append("=");
                    builder.append(URLEncoder.encode(protagonista,"UTF-8"));
                    builder.append("&");
                    builder.append("idfoto");
                    builder.append("=");
                    builder.append(URLEncoder.encode(idfoto,"UTF-8"));

                    String query = builder.toString();

                    conexion.setFixedLengthStreamingMode(query.getBytes("UTF-8").length);

                    OutputStream outputStream = conexion.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    bufferedWriter.write(query);
                    bufferedWriter.flush();
                    bufferedWriter.close();

                    //Conectar
                    conexion.connect();

                    //Leer Respuesta del servidor

                    InputStream inputStream = conexion.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder datos = new StringBuilder();
                    String linea;
                    while((linea =bufferedReader.readLine())!=null){
                        datos.append(linea);
                    }

                    bufferedReader.close();
                    return datos.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;

            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    boolean success = jsonObject.getBoolean("success");
                    if(success){
                        urlfoto = jsonObject.getString("foto");
                        guardarLocal(contexto);

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute();
    }




    public void guardarLocal(Context contexto){
        //declarar las variables
        SQLiteDatabase db;
        String sql;

        //Abrir la conexion de base datos en modo escritura
        SeriesSQLiteOpenHelper  aux = new SeriesSQLiteOpenHelper(contexto,"DBSeries",null);
        db = aux.getWritableDatabase();

        //insertar forma 1
        sql = "INSERT INTO Series values('"
                +this.getUuid()+"','"
                +this.getUrlfoto()+"','"
                +this.getAnio()+"','"
                +this.getNombre()+"','"
                +this.getDescripcion()+"','"
                +this.getProtagonista()+"','"
                +this.getIdfoto()+"')";

        db.execSQL(sql);

        //cerrar conexion
        db.close();

    }
}
