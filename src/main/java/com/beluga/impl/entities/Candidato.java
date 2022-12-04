package com.beluga.impl.entities;

public class Candidato {
    private String nombre;
    private int votos;

    public int getVotos() {
        return votos;
    }

    public void addVoto() {
        this.votos++;
    }

    public Candidato(String nombre) {
        this.nombre = nombre;
        this.votos = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
