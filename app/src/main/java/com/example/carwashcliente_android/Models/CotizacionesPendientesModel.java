package com.example.carwashcliente_android.Models;

public class CotizacionesPendientesModel {
    private int idCotizacion;
    private String modalidad;
    private String fechaCita;
    private String total;

    public CotizacionesPendientesModel(int idCotizacion, String modalidad, String fechaCita, String total) {
        this.idCotizacion = idCotizacion;
        this.modalidad = modalidad;
        this.fechaCita = fechaCita;
        this.total = total;
    }

    public int getIdCotizacion() {
        return idCotizacion;
    }

    public String getModalidad() {
        return modalidad;
    }

    public String getFechaCita() {
        return fechaCita;
    }

    public String getTotal() {
        return total;
    }
}

