package com.example.pngweb.tellerfinal;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetalleSerie extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Series s;
    private String anio,nombre,descripción,protagonista,urlfoto;
    private Bundle b;
    private TextView desc ;
    private Intent i;
    private ImageView foto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_serie);
        desc=(TextView)findViewById(R.id.descripcionSerie);
        Toolbar toolbar= (Toolbar)findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        i = getIntent();
        b=i.getBundleExtra("datos");
        anio = b.getString("anio");
        nombre = b.getString("nombre");
        descripción = b.getString("descripcion");
        protagonista = b.getString("protagonista");
        urlfoto = b.getString("urlfoto");

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        foto = (ImageView)findViewById(R.id.fotoSerie);

        Picasso.with(getApplicationContext()).load(urlfoto).into(foto);
        collapsingToolbarLayout.setTitle(nombre+" ");
        desc.setText(descripción);

    }
}
