package com.example.carwashcliente_android.Models;

public class UbicacionModel {
    int id;
    String nombre;
    String referencia;
    double lat;
    double longi;

    public UbicacionModel() {
    }

    public UbicacionModel(int id, String nombre, String referencia, double lat, double longi) {
        this.id = id;
        this.nombre = nombre;
        this.referencia = referencia;
        this.lat = lat;
        this.longi = longi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }
}
