package com.example.carwashcliente_android.Models;

import java.io.Serializable;
import java.util.List;

public class Cotizacion implements Serializable {
    private int idCarro;
    private String servicio;
    private int modalidad;
    private double precio;
    private String fechaCita;
    private String estado;
    private List<Cotizacion.detalles>detalles;

    public Cotizacion() {
    }


    public Cotizacion(String servicio, int modalidad, double precio, String fechaCita, String estado) {
        this.servicio = servicio;
        this.modalidad = modalidad;
        this.precio = precio;
        this.fechaCita = fechaCita;
        this.estado = estado;
    }

    public Cotizacion(String servicio, int modalidad, int idCarro, double precio, String fecha, String estado, List<Cotizacion.detalles> detalles) {
        this.servicio = servicio;
        this.modalidad = modalidad;
        this.precio = precio;
        this.fechaCita = fecha;
        this.estado = estado;
        this.detalles = detalles;
        this.idCarro = idCarro;
    }

    public int getModalidad() {
        return modalidad;
    }

    public void setModalidad(int modalidad) {
        this.modalidad = modalidad;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(String fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Cotizacion.detalles> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Cotizacion.detalles> detalles) {
        this.detalles = detalles;
    }

    public int getIdCarro() {
        return idCarro;
    }

    public void setIdCarro(int idCarro) {
        this.idCarro = idCarro;
    }

    public void addDetalles(Cotizacion.detalles detalles) {
        this.detalles.add(detalles);
    }

    public static class detalles implements Serializable {
        private int idServicio;
        private String notaCliente;
        private double precio;

        public detalles(int idServicio, String notaCliente, double precio) {
            this.idServicio = idServicio;
            this.notaCliente = notaCliente;
            this.precio = precio;
        }

        public int getIdServicio() {
            return idServicio;
        }

        public void setIdServicio(int idServicio) {
            this.idServicio = idServicio;
        }

        public String getNotaCliente() {
            return notaCliente;
        }

        public void setNotaCliente(String notaCliente) {
            this.notaCliente = notaCliente;
        }

        public double getPrecio() {
            return precio;
        }

        public void setPrecio(double precio) {
            this.precio = precio;
        }
    }
}