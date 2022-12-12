package com.beluga.impl.urnaVirtual;

import java.util.ArrayList;

import com.beluga.framework.mvc.Model;
import com.beluga.impl.entities.Candidato;


public class UrnaVirtual extends Model {

    private ArrayList<Candidato> candidatos;

    public UrnaVirtual() {
        this.candidatos = new ArrayList<>();
        // Hardcoded for a simple use only
        Candidato uno = new Candidato("Tony");
        Candidato dos = new Candidato("Gabo");
        Candidato tres = new Candidato("Teo");
        candidatos.add(uno);
        candidatos.add(dos);
        candidatos.add(tres);
    }

    public ArrayList<Candidato> getCandidatos() {
        return candidatos;
    }

    public void addVotoToCandidatoWithNombre(Object nombre) {
        Candidato elected = this.getCandidatoWithNombre((String) nombre);
        if (elected != null) {
            elected.addVoto();
            this.statusChanged();
        }

    }

    private Candidato getCandidatoWithNombre(String nombre) {
        for (Candidato candidato : this.candidatos) {
            if (candidato.getNombre().equals(nombre)) {
                return candidato;
            }

        }
        // Will be managed later (Using happy path by now)
        return null;
    }

}
