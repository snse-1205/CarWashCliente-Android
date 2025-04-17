package com.example.carwashcliente_android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashcliente_android.Models.Cotizacion;
import com.example.carwashcliente_android.Models.UbicacionModel;
import com.example.carwashcliente_android.R;

import java.util.List;

public class UbicacionAdapter extends RecyclerView.Adapter<UbicacionAdapter.ViewHolder>{
    private List<UbicacionModel> ubicaciones;
    private Context context;

    public UbicacionAdapter(List<UbicacionModel> ubicaciones, Context context) {
        this.ubicaciones = ubicaciones;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_ubicacion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UbicacionModel ubicacion = ubicaciones.get(position);
        holder.tvNombre.setText(ubicacion.getNombre());
        holder.tvReferencia.setText(ubicacion.getReferencia());
    }

    @Override
    public int getItemCount() {
        return ubicaciones.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNombre, tvReferencia;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreUbicacion);
            tvReferencia = itemView.findViewById(R.id.tvReferencia);
        }
    }

}
