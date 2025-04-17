package com.example.carwashcliente_android.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashcliente_android.Models.ServiciosModel;
import com.example.carwashcliente_android.R;

import java.util.List;

public class SeleccionarServicioAdapter extends RecyclerView.Adapter<SeleccionarServicioAdapter.ViewHolder>{

    private ServicioClickListener listener;
    List<ServiciosModel> serviciosList;

    public SeleccionarServicioAdapter(List<ServiciosModel> serviciosList,ServicioClickListener listener ) {
        this.listener = listener;
        this.serviciosList = serviciosList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servicio_cotizacion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiciosModel servicio = serviciosList.get(position);
        holder.scServicio.setText(servicio.getServicio());
        holder.scPrecio.setText(String.valueOf(servicio.getPrecio()));

        holder.itemView.setOnClickListener(v -> {
            if(listener != null){
                listener.onServicioSeleccionado(servicio);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviciosList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView scServicio,scPrecio;

        public ViewHolder(View itemView) {
            super(itemView);
            scServicio = itemView.findViewById(R.id.scServicio);
            scPrecio = itemView.findViewById(R.id.scPrecio);
        }
    }

    public interface ServicioClickListener {
        void onServicioSeleccionado(ServiciosModel servicio);
    };
}
