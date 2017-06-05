package com.example.pngweb.tellerfinal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;

public class RegistrarSerie extends AppCompatActivity {
    private EditText cajaano;
    private EditText cajaNombre;
    private EditText cajaDescripcion;
    private EditText cajaProtagonista;
    private boolean guardado;
    private TextInputLayout icajaano;
    private TextInputLayout icajaNombre;
    private TextInputLayout icajaDescripcion;
    private TextInputLayout icajaProtagonista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_serie);

        cajaano = (EditText)findViewById(R.id.txtAno);
        cajaNombre= (EditText)findViewById(R.id.txtNombreSerie);
        cajaDescripcion = (EditText)findViewById(R.id.txtDescripcionSerie);
        cajaProtagonista = (EditText)findViewById(R.id.txtProtagonistaSerie);


        icajaano = (TextInputLayout) findViewById(R.id.anoSerie);
        icajaNombre = (TextInputLayout)findViewById(R.id.nombreSerie);
        icajaDescripcion = (TextInputLayout)findViewById(R.id.descripcionSerie);
        icajaProtagonista = (TextInputLayout)findViewById(R.id.protagonistaSerie);
        guardado = false;
        cajaano.addTextChangedListener(new TextWatcherPersonalizado(icajaano,getResources().getString(R.string.mensaje_error_anio)) {
            @Override
            public boolean estaVacio(Editable s) {
                if(TextUtils.isEmpty(s) && !guardado) return true;
                else return false;
            }
        });


        cajaNombre.addTextChangedListener(new TextWatcherPersonalizado(icajaNombre,getResources().getString(R.string.mensaje_error_nombre)) {
            @Override
            public boolean estaVacio(Editable s) {
                if(TextUtils.isEmpty(s)&& !guardado) return true;
                else return false;
            }
        });


        cajaDescripcion.addTextChangedListener(new TextWatcherPersonalizado(icajaDescripcion,getResources().getString(R.string.mensaje_error_descripcion)) {
            @Override
            public boolean estaVacio(Editable s) {
                if(TextUtils.isEmpty(s)&& !guardado) return true;
                else return false;
            }
        });

        cajaProtagonista.addTextChangedListener(new TextWatcherPersonalizado(icajaProtagonista,getResources().getString(R.string.mensaje_error_protagonista)) {
            @Override
            public boolean estaVacio(Editable s) {
                if(TextUtils.isEmpty(s)&& !guardado) return true;
                else return false;
            }
        });

    }




    public int fotoAleatoria(){
        int fotos[] = {R.drawable.images,R.drawable.images2,R.drawable.image3};
        int numero = (int)(Math.random() * 3);
        return fotos[numero];
    }
    public void guardar(View v)  {
        String urlfoto,anio,nombre,descripcion,protagonista,idfoto;
        Series s;
        int foto;

        if(validarTodo()){
            anio = cajaano.getText().toString();
            nombre = cajaNombre.getText().toString();
            descripcion=cajaDescripcion.getText().toString();
            protagonista=cajaProtagonista.getText().toString();

            foto = fotoAleatoria();
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),foto);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
            byte[] imagenBytes = baos.toByteArray();
            urlfoto = Base64.encodeToString(imagenBytes,Base64.DEFAULT);
            try {
                baos.close();
            }catch (Exception e){

            }
            idfoto=String.valueOf(foto);
            s = new Series(urlfoto,anio,nombre,descripcion,protagonista,idfoto);
            s.guardar(getApplicationContext());

            InputMethodManager imp =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imp.hideSoftInputFromWindow(cajaano.getWindowToken(),0);
            Snackbar.make(v,getResources().getString(R.string.mensaje_exitoso_guardar),Snackbar.LENGTH_SHORT).show();
            guardado= true;
            limpiar();


        }
    }

    public void limpiar(){
        cajaano.setText("");
        cajaNombre.setText("");
        cajaDescripcion.setText("");
        cajaProtagonista.setText("");
        cajaano.requestFocus();

        guardado = false;
    }
    public boolean validarTodo(){
        if(cajaano.getText().toString().isEmpty()){
            icajaano.setError(getResources().getString(R.string.mensaje_error_anio));
            cajaano.requestFocus();
            return false;
        }
        if(cajaNombre.getText().toString().isEmpty()){
            icajaNombre.setError(getResources().getString(R.string.mensaje_error_nombre));
            cajaNombre.requestFocus();
            return false;
        }
        if(cajaDescripcion.getText().toString().isEmpty()){
            icajaDescripcion.setError(getResources().getString(R.string.mensaje_error_descripcion));
            cajaDescripcion.requestFocus();
            return false;
        }
        if(cajaProtagonista.getText().toString().isEmpty()){
            icajaProtagonista.setError(getResources().getString(R.string.mensaje_error_protagonista));
            cajaProtagonista.requestFocus();
            return false;
        }


        return true;
    }

    public void onBackPressed(){
        finish();
        Intent i = new Intent(RegistrarSerie.this,Principal.class);
        startActivity(i);
    }
}
