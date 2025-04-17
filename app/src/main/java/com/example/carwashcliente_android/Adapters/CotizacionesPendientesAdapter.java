package com.example.carwashcliente_android.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashcliente_android.Models.CotizacionesPendientesModel;
import com.example.carwashcliente_android.Models.VehiculoModel;
import com.example.carwashcliente_android.R;

import java.util.List;

public class CotizacionesPendientesAdapter extends RecyclerView.Adapter<CotizacionesPendientesAdapter.ViewHolder> {

    private List<CotizacionesPendientesModel> cotizacionesPendientes;
    private CotizacionesPendientesAdapter.ServicioClickListener listener;
    private CotizacionesPendientesAdapter.ServicioClickListener2 listener2;

    public CotizacionesPendientesAdapter(List<CotizacionesPendientesModel> cotizacionesPendientes, CotizacionesPendientesAdapter.ServicioClickListener listener, CotizacionesPendientesAdapter.ServicioClickListener2 listener2) {
        this.cotizacionesPendientes = cotizacionesPendientes;
        this.listener = listener;
        this.listener2 = listener2;
    }

    @NonNull
    @Override
    public CotizacionesPendientesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cotizaciones_pendientes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CotizacionesPendientesAdapter.ViewHolder holder, int position) {
        CotizacionesPendientesModel cotizacion = cotizacionesPendientes.get(position);

        holder.tvIdCotizacion.setText("#" + cotizacion.getIdCotizacion());
        holder.tvModalidad.setText("Modalidad: " + cotizacion.getModalidad());
        holder.tvFechaCita.setText("Fecha: " + cotizacion.getFechaCita());
        holder.tvTotal.setText("$" + cotizacion.getTotal());

        holder.btnAceptar.setOnClickListener(v -> {
            if(listener != null){
                listener.onServicioSeleccionado(cotizacion);
            }
        });

        holder.btnRechazar.setOnClickListener(v -> {
            if(listener2 != null){
                listener2.onServicioSeleccionado(cotizacion);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cotizacionesPendientes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvIdCotizacion, tvModalidad, tvFechaCita, tvTotal;
        Button btnAceptar, btnRechazar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIdCotizacion = itemView.findViewById(R.id.tv_id_cotizacion);
            tvModalidad = itemView.findViewById(R.id.tv_modalidad);
            tvFechaCita = itemView.findViewById(R.id.tv_fecha_cita);
            tvTotal = itemView.findViewById(R.id.tv_total);
            btnAceptar = itemView.findViewById(R.id.btn_aceptar);
            btnRechazar = itemView.findViewById(R.id.btn_rechazar);
        }
    }

    public interface ServicioClickListener {
        void onServicioSeleccionado(CotizacionesPendientesModel servicio);
    };

    public interface ServicioClickListener2 {
        void onServicioSeleccionado(CotizacionesPendientesModel servicio);
    };
}
