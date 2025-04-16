package com.example.carwashcliente_android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashcliente_android.Models.Cotizacion;
import com.example.carwashcliente_android.R;

import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.ViewHolder> {

    private List<Cotizacion> cotizaciones;
    private Context context;

    public HistorialAdapter(List<Cotizacion> cotizaciones, Context context) {
        this.cotizaciones = cotizaciones;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historial, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cotizacion cotizacion = cotizaciones.get(position);
        holder.tvServicio.setText(cotizacion.getServicio());
        holder.tvPrecio.setText(String.valueOf(cotizacion.getPrecio()));
        holder.tvFecha.setText(cotizacion.getFechaCita());
        holder.tvEstado.setText(cotizacion.getEstado());
    }

    @Override
    public int getItemCount() {
        return cotizaciones.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvServicio, tvPrecio, tvFecha, tvEstado;

        public ViewHolder(View itemView) {
            super(itemView);
            tvServicio = itemView.findViewById(R.id.tvServicio);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvEstado = itemView.findViewById(R.id.tvEstado);
        }
    }
}