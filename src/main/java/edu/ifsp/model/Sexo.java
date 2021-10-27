package edu.ifsp.model;

import java.util.Arrays;

public enum Sexo {
    FEMININO("Feminino"),
    MASCULINO("Masculino");


    private String label;


    Sexo(String label) {
        this.label = label;
    }


    @Override
    public String toString() {
        return label;
    }


    public static Sexo toEnum(String value) {
        return Arrays.stream(Sexo.values())
                .filter(s -> value.equals(s.toString()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
