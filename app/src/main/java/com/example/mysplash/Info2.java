package com.example.mysplash;


import java.io.Serializable;

public class Info2 implements Serializable {

    private String ContraContra;
    private int id_pass;
    private String UsuarioContra;
    private double longitud;
    private double latitud;
    private int id_user;
    private byte[] bytes;
    private int imagen;

    public String getContraContra() {
        return ContraContra;
    }

    public void setContraContra(String contraContra) {
        ContraContra = contraContra;
    }

    public String getUsuarioContra() {
        return UsuarioContra;
    }

    public void setUsuarioContra(String usuarioContra) {
        UsuarioContra = usuarioContra;
    }

    public int getId_pass() {
        return id_pass;
    }

    public void setId_pass(int id_pass) {
        this.id_pass = id_pass;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
