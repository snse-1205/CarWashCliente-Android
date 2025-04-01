package com.example.carwashcliente_android.Models;


public class Vehiculo {
    private String marca;
    private String modelo;
    private String placa;
    private String anio;

    public Vehiculo(String marca, String modelo, String placa, String anio) {
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.anio = anio;
    }

    // Getters
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public String getPlaca() { return placa; }
    public String getAnio() { return anio; }
}