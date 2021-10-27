package edu.ifsp.model;

import java.time.LocalDate;

public class Amigo extends Colega {

    private String coisaPreferida;


    public Amigo(int id, String nome, LocalDate nascimento, Sexo sexo, Endereco endereco, Grupo grupo, String coisaPreferida) {
        super(id, nome, nascimento, sexo, endereco, grupo);
        this.coisaPreferida = coisaPreferida;
    }


    public String getCoisaPreferida() {
        return coisaPreferida;
    }


    public void setCoisaPreferida(String coisaPreferida) {
        this.coisaPreferida = coisaPreferida;
    }
}
