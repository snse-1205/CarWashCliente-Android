package com.example.carwashcliente_android.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashcliente_android.Activities.RegistroVehiculo;
import com.example.carwashcliente_android.Models.ServiciosModel;
import com.example.carwashcliente_android.Models.VehiculoModel;
import com.example.carwashcliente_android.R;

import java.util.List;

public class VehiculoAdapter extends RecyclerView.Adapter<VehiculoAdapter.ViewHolder> {

    private List<VehiculoModel> vehiculos;
    private VehiculoAdapter.ServicioClickListener listener;
    private VehiculoAdapter.ServicioClickListener2 listener2;

    public VehiculoAdapter(List<VehiculoModel> vehiculos, ServicioClickListener listener, ServicioClickListener2 listener2) {
        this.vehiculos = vehiculos;
        this.listener = listener;
        this.listener2 =listener2;
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
        holder.tvColor.setText(vehiculo.getMarca());

        holder.btnEditar.setOnClickListener(
                v -> {
                    if(listener != null){
                        listener.onServicioSeleccionado(vehiculo);
                    }
                }
        );

        holder.btnEliminar.setOnClickListener(
                v -> {
                    if(listener2 != null){
                        listener2.onServicioSeleccionado(vehiculo);
                    }
                }
        );

    }

    @Override
    public int getItemCount() {
        return vehiculos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMarca, tvModelo, tvPlaca, tvAnio, tvColor;
        public Button btnEditar, btnEliminar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMarca = itemView.findViewById(R.id.tvMarcaModelo);
            tvModelo = itemView.findViewById(R.id.tvModelo);
            tvPlaca = itemView.findViewById(R.id.tvPlaca);
            tvAnio = itemView.findViewById(R.id.tvAnio);
            tvColor = itemView.findViewById(R.id.tvColor);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }

    public interface ServicioClickListener {
        void onServicioSeleccionado(VehiculoModel servicio);
    };

    public interface ServicioClickListener2 {
        void onServicioSeleccionado(VehiculoModel servicio);
    };
}