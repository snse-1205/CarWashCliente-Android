package com.example.carwashcliente_android.Models;



public class Cotizacion {
    private String servicio;
    private String precio;
    private String fecha;
    private String estado;

    public Cotizacion(String servicio, String precio, String fecha, String estado) {
        this.servicio = servicio;
        this.precio = precio;
        this.fecha = fecha;
        this.estado = estado;
    }

    // Getters
    public String getServicio() { return servicio; }
    public String getPrecio() { return precio; }
    public String getFecha() { return fecha; }
    public String getEstado() { return estado; }
}