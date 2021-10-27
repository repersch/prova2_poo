package edu.ifsp.model;

import java.time.LocalDate;

public class Colega {

    private int id;
    private String nome;
    private LocalDate nascimento;
    private Sexo sexo;
    private Endereco endereco;
    private Grupo grupo;


    public Colega(int id, String nome, LocalDate nascimento, Sexo sexo, Endereco endereco, Grupo grupo) {
        this.id = id;
        this.nome = nome;
        this.nascimento = nascimento;
        this.sexo = sexo;
        this.endereco = endereco;
        this.grupo = grupo;
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


    public LocalDate getNascimento() {
        return nascimento;
    }


    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }


    public Sexo getSexo() {
        return sexo;
    }


    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }


    public Endereco getEndereco() {
        return endereco;
    }


    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }


    public Grupo getGrupo() {
        return grupo;
    }


    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
}
