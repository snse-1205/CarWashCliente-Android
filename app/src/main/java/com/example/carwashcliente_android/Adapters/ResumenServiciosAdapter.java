package com.example.carwashcliente_android.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashcliente_android.Models.Cotizacion;
import com.example.carwashcliente_android.R;

import java.util.List;

public class ResumenServiciosAdapter extends RecyclerView.Adapter<ResumenServiciosAdapter.ViewHolder> {

    private final List<Cotizacion.detalles> detallesList;

    public ResumenServiciosAdapter(List<Cotizacion.detalles> detallesList) {
        this.detallesList = detallesList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtIdServicio, txtNotaCliente, txtPrecio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdServicio = itemView.findViewById(R.id.txtIdServicio);
            txtNotaCliente = itemView.findViewById(R.id.txtNotaCliente);
            txtPrecio = itemView.findViewById(R.id.txtPrecioServicio);
        }
    }

    @NonNull
    @Override
    public ResumenServiciosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_resumen_servicio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResumenServiciosAdapter.ViewHolder holder, int position) {
        Cotizacion.detalles detalle = detallesList.get(position);

        holder.txtIdServicio.setText("ID Servicio: " + detalle.getIdServicio());
        holder.txtNotaCliente.setText("Nota: " + detalle.getNotaCliente());
        holder.txtPrecio.setText("Precio: $" + detalle.getPrecio());
    }

    @Override
    public int getItemCount() {
        return detallesList.size();
    }
}
