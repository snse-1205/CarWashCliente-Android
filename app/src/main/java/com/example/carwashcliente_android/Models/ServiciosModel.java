package com.example.carwashcliente_android.Models;

public class ServiciosModel {
    private int ID;
    private String Servicio;
    private double Precio;
    private int categoria;

    public ServiciosModel() {
    }

    public ServiciosModel(int ID, String servicio, double precio, int categoria) {
        this.ID = ID;
        Servicio = servicio;
        Precio = precio;
        this.categoria = categoria;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getServicio() {
        return Servicio;
    }

    public void setServicio(String servicio) {
        Servicio = servicio;
    }

    public double getPrecio() {
        return Precio;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public void setPrecio(double precio) {
        Precio = precio;
    }
}
