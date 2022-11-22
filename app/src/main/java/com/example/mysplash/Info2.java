package com.example.mysplash;


import java.io.Serializable;

public class Info2 implements Serializable {

    private String ContraContra;
    private int img;
    private String UsuarioContra;

    public String getContraContra() {
        return ContraContra;
    }

    public void setContraContra(String contraContra) {
        ContraContra = contraContra;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getUsuarioContra() {
        return UsuarioContra;
    }

    public void setUsuarioContra(String usuarioContra) {
        UsuarioContra = usuarioContra;
    }
}
