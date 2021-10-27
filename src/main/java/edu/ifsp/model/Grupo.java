package edu.ifsp.model;

import java.util.ArrayList;
import java.util.List;

public class Grupo {

    private int id;
    private String nome;


    private List<Colega> colegas = new ArrayList<>();


    public Grupo(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public List<Colega> getColegas() {
        return colegas;
    }


    public void setColegas(List<Colega> colegas) {
        this.colegas = colegas;
    }


    @Override
    public String toString() {
        return nome;
    }
}
