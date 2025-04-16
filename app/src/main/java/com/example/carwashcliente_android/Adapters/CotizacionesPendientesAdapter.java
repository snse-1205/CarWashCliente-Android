package com.example.carwashcliente_android.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashcliente_android.Models.Cotizacion;
import com.example.carwashcliente_android.Models.CotizacionesPendientesModel;
import com.example.carwashcliente_android.R;

import java.util.List;

public class CotizacionesPendientesAdapter extends RecyclerView.Adapter<CotizacionesPendientesAdapter.ViewHolder> {

    private List<CotizacionesPendientesModel> cotizacionesPendientes;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
