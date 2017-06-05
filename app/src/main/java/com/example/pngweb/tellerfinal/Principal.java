package com.example.pngweb.tellerfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class Principal extends AppCompatActivity implements AdaptadorSeries.OnSeriesClickListener {
    private RecyclerView listado;
    private ArrayList<Series> series;
    private AdaptadorSeries adapter;
    private LinearLayoutManager llm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        listado = (RecyclerView) findViewById(R.id.lstOpciones);

        series = Datos.traerSeries(getApplicationContext());
        adapter = new AdaptadorSeries(series,this);

        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        listado.setLayoutManager(llm);
        listado.setAdapter(adapter);

    }

    public void agregar(View v){
        finish();
        Intent i = new Intent(Principal.this, RegistrarSerie.class);
        startActivity(i);
    }

    @Override
    public void onSeriesClick(Series s) {
        //finish();
        Intent i = new Intent(Principal.this,DetalleSerie.class);
        Bundle b = new Bundle();
        b.putString("anio",s.getAnio());
        b.putString("nombre",s.getNombre());
        b.putString("descripcion",s.getDescripcion());
        b.putString("protagonista",s.getProtagonista());
        b.putString("urlfoto",s.getUrlfoto());

        i.putExtra("datos",b);
        startActivity(i);
    }
}
