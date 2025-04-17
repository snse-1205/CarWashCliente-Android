package com.example.carwashcliente_android.Models;

public class UsuarioModel {
    private int id;
    private String username;
    private String token;
    private Integer codigo;
    private String mensaje;

    public UsuarioModel() {
    }

    public UsuarioModel(int id, String username, String accessToken) {
        this.id = id;
        this.username = username;
        this.token = accessToken;
    }

    public UsuarioModel(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public UsuarioModel(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String toString() {
        return "UsuarioModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
