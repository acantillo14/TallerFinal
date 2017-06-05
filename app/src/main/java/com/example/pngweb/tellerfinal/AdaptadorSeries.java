package com.example.pngweb.tellerfinal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;



public class AdaptadorSeries extends RecyclerView.Adapter<AdaptadorSeries.SeriesViewHolder> {

    private ArrayList<Series> series;
    private OnSeriesClickListener clickListener;

    public AdaptadorSeries(ArrayList<Series> series, AdaptadorSeries.OnSeriesClickListener clickListener){
        this.series=series;
        this.clickListener=clickListener;
    }

    @Override
    public AdaptadorSeries.SeriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_series,parent,false);
        return new AdaptadorSeries.SeriesViewHolder(v);
    }



    @Override
    public void onBindViewHolder(AdaptadorSeries.SeriesViewHolder holder, int position) {
        final Series s = series.get(position);
        Picasso.with(holder.view.getContext()).load(s.getUrlfoto()).into(holder.foto);

        holder.anio.setText(s.getAnio());
        holder.nombre.setText(s.getNombre());
        holder.descripcion.setText(s.getDescripcion());
        holder.protagonista.setText(s.getProtagonista());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onSeriesClick(s);
            }
        });
    }

    @Override
    public int getItemCount() {
        return series.size();
    }

    public static class SeriesViewHolder extends RecyclerView.ViewHolder{
        private ImageView foto;
        private TextView anio;
        private TextView nombre;
        private TextView descripcion;
        private TextView protagonista;
        private View view;

        public SeriesViewHolder(View itemView) {
            super(itemView);
            view= itemView;
            foto = (ImageView)itemView.findViewById(R.id.foto);
            anio = (TextView)itemView.findViewById(R.id.txtAnoS);
            nombre = (TextView) itemView.findViewById(R.id.txtNombreS);
            descripcion = (TextView)itemView.findViewById(R.id.txtProtagonistaS);
            protagonista = (TextView)itemView.findViewById(R.id.txtProtagonistaS);
        }
    }

    public interface OnSeriesClickListener{
        void onSeriesClick(Series s);
    }

}

