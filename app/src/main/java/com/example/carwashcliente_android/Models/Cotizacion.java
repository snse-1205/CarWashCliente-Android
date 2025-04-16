package com.example.carwashcliente_android.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cotizacion implements Serializable {
    private int idCarro;
    private String servicio;
    private int modalidad;
    private double total;  // Cambié 'precio' por 'total'
    private String fechaCita;
    private String estado;
    private int ubicacion;
    private List<Cotizacion.detalles> detalles = new ArrayList<>();

    public Cotizacion() {
    }

    public Cotizacion(int idCarro, String servicio, int modalidad, double total, String fechaCita, String estado, int ubicacion) {
        this.idCarro = idCarro;
        this.servicio = servicio;
        this.modalidad = modalidad;
        this.total = total;
        this.fechaCita = fechaCita;
        this.estado = estado;
        this.ubicacion = ubicacion;
    }

    public Cotizacion(int idCarro, String servicio, int modalidad, double total, String fechaCita, String estado) {
        this.idCarro = idCarro;
        this.servicio = servicio;
        this.modalidad = modalidad;
        this.total = total;
        this.fechaCita = fechaCita;
        this.estado = estado;
    }

    public Cotizacion(String servicio, int modalidad, int idCarro, double total, String fecha, String estado, List<Cotizacion.detalles> detalles) {
        this.servicio = servicio;
        this.modalidad = modalidad;
        this.total = total;
        this.fechaCita = fecha;
        this.estado = estado;
        this.detalles = detalles;
        this.idCarro = idCarro;
    }

    public void calcularTotal() {
        double sumaTotal = 0.0;
        for (detalles detalle : detalles) {
            sumaTotal += detalle.getPrecio();  // Suma los precios de los detalles
        }
        this.total = sumaTotal;  // Actualiza el total con la suma
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

    public double getTotal() {  // Renombrado a 'getTotal'
        return total;
    }

    public void setTotal(double total) {  // Renombrado a 'setTotal'
        this.total = total;
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

    public int getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(int ubicacion) {
        this.ubicacion = ubicacion;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cotizacion {\n");
        sb.append("  idCarro: ").append(idCarro).append("\n");
        sb.append("  servicio: ").append(servicio).append("\n");
        sb.append("  modalidad: ").append(modalidad).append("\n");
        sb.append("  total: ").append(total).append("\n");
        sb.append("  fechaCita: ").append(fechaCita).append("\n");
        sb.append("  estado: ").append(estado).append("\n");

        sb.append("  detalles:\n");
        for (detalles d : detalles) {
            sb.append("    - idServicio: ").append(d.getIdServicio()).append("\n");
            sb.append("      notaCliente: ").append(d.getNotaCliente()).append("\n");
            sb.append("      precio: ").append(d.getPrecio()).append("\n");
        }

        sb.append("}");
        return sb.toString();
    }
}
