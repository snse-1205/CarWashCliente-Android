package com.example.carwashcliente_android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashcliente_android.Models.VehiculoModel;
import com.example.carwashcliente_android.R;

import java.util.List;

public class VehiculoAdapter extends RecyclerView.Adapter<VehiculoAdapter.ViewHolder> {

    private List<VehiculoModel> vehiculos;
    private Context context;

    public VehiculoAdapter(List<VehiculoModel> vehiculos, Context context) {
        this.vehiculos = vehiculos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vehiculo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VehiculoModel vehiculo = vehiculos.get(position);
        holder.tvMarca.setText(vehiculo.getMarca());
        holder.tvModelo.setText(vehiculo.getModelo());
        holder.tvPlaca.setText(vehiculo.getPlaca());
        holder.tvAnio.setText(vehiculo.getAnio());
    }

    @Override
    public int getItemCount() {
        return vehiculos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMarca, tvModelo, tvPlaca, tvAnio;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMarca = itemView.findViewById(R.id.tvMarcaModelo);
            tvModelo = itemView.findViewById(R.id.tvAnio);
            tvPlaca = itemView.findViewById(R.id.tvPlaca);
            tvAnio = itemView.findViewById(R.id.tvAnio);
        }
    }
}