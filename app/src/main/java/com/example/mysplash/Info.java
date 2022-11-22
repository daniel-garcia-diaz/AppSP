package com.example.mysplash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Info implements Serializable {

    private String nomCompleto;
    private String email;
    private String telefono;
    private String edad;
    private String user;
    private String contra;
    private String sexo;
    private String[] gustos;
    private String[] redes;
    private List<Info2>contraseñas = new ArrayList<>();


    public Info(){

    }

    public String getNomCompleto() {
        return nomCompleto;
    }

    public void setNomCompleto(String nomCompleto) {
        this.nomCompleto = nomCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String[] getGustos() {
        return gustos;
    }

    public void setGustos(String[] gustos) {
        this.gustos = gustos;
    }

    public String[] getRedes() {
        return redes;
    }

    public void setRedes(String[] redes) {
        this.redes = redes;
    }

    public List<Info2> getContraseñas(){
        return contraseñas;
    }
    public void setContraseñas(List<Info2>contraseñas){
        this.contraseñas = contraseñas;
    }
}
