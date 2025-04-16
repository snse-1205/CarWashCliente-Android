package com.example.carwashcliente_android.Models;


import java.util.List;

public class VehiculoModel {
    private String marca;
    private String modelo;
    private String placa;
    private String anio;
    private int id;

    public class Modelo {
        private int id;
        private String nombre;

        public Modelo(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        public int getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }
    }

    public class Marca {
        private int id;
        private String nombre;
        private List<Modelo> modelos;

        public Marca(int id, String nombre, List<Modelo> modelos) {
            this.id = id;
            this.nombre = nombre;
            this.modelos = modelos;
        }

        public int getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        public List<Modelo> getModelos() {
            return modelos;
        }
    }

    public VehiculoModel(int id, String marca, String modelo, String placa, String anio) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.anio = anio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public String getPlaca() { return placa; }
    public String getAnio() { return anio; }
}