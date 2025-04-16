package com.example.carwashcliente_android.Models;

public class ServiciosModel {
    private int ID;
    private String Servicio;
    private double Precio;

    public ServiciosModel() {
    }

    public ServiciosModel(int ID, String servicio, double precio) {
        this.ID = ID;
        Servicio = servicio;
        Precio = precio;
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

    public void setPrecio(double precio) {
        Precio = precio;
    }
}
