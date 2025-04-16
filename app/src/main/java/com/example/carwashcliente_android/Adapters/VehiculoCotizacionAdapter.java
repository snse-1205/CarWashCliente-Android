package com.example.carwashcliente_android.Adapters;

import androidx.fragment.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashcliente_android.Fragments.LocalFragment;
import com.example.carwashcliente_android.Fragments.SeleccionServiciosFragment;
import com.example.carwashcliente_android.Models.Cotizacion;
import com.example.carwashcliente_android.Models.VehiculoModel;
import com.example.carwashcliente_android.R;

import java.util.List;

public class VehiculoCotizacionAdapter extends RecyclerView.Adapter<VehiculoCotizacionAdapter.ViewHolder> {
    private VehiculoClickListener listener;
    private List<VehiculoModel> vehiculos;
    Cotizacion cotizacion;

    public VehiculoCotizacionAdapter(List<VehiculoModel> vehiculos, VehiculoClickListener listener) {
        this.vehiculos = vehiculos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vehiculo_cotizacion, parent, false);
        return new VehiculoCotizacionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VehiculoModel vehiculo = vehiculos.get(position);
        holder.tvMarca.setText(vehiculo.getMarca());
        holder.tvModelo.setText(vehiculo.getModelo());
        holder.tvPlaca.setText(vehiculo.getPlaca());
        holder.tvAnio.setText(vehiculo.getAnio());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onVehiculoSeleccionado(vehiculo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vehiculos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMarca,tvModelo, tvPlaca, tvAnio;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMarca = itemView.findViewById(R.id.tvMarcaModelo);
            tvModelo = itemView.findViewById(R.id.tvModelo);
            tvPlaca = itemView.findViewById(R.id.tvPlaca);
            tvAnio = itemView.findViewById(R.id.tvAnio);
        }
    }

    public interface VehiculoClickListener {
        void onVehiculoSeleccionado(VehiculoModel vehiculo);
    }

}
