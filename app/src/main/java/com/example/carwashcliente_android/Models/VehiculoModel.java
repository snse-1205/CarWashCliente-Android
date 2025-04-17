package com.example.carwashcliente_android.Models;

import java.util.List;

public class VehiculoModel {
    // Datos del vehículo (cuando el usuario ya ha registrado uno)
    private int id;
    private String marca;
    private String modelo;
    private String placa;
    private String anio;
    private String color;

    public VehiculoModel(int id, String marca, String modelo, String placa, String anio) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.anio = anio;
    }

    public VehiculoModel(int id, String marca, String modelo, String placa, String anio, String color) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.anio = anio;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public VehiculoModel() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getAnio() { return anio; }
    public void setAnio(String anio) { this.anio = anio; }

    // ---------- Datos para deserializar el JSON de marcas y modelos ----------

    public static class Marca {
        public int id;
        public String nombre;
        public List<Modelo> modelos;

        public Marca() {} // Necesario para deserialización con Gson

        public Marca(int id, String nombre, List<Modelo> modelos) {
            this.id = id;
            this.nombre = nombre;
            this.modelos = modelos;
        }

        public int getId() { return id; }
        public String getNombre() { return nombre; }
        public List<Modelo> getModelos() { return modelos; }
    }

    public static class Modelo {
        public int id;
        public String nombre;

        public Modelo() {}

        public Modelo(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        public int getId() { return id; }
        public String getNombre() { return nombre; }
    }
}
