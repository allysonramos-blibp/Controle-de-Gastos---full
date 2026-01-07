package com.allyson.controlegastos.model;

public enum Categoria {
    ALIMENTACAO("Alimentação"),
    TRANSPORTE("Transporte"),
    MORADIA("Moradia"),
    LAZER("Lazer"),
    SAUDE("Saúde"),
    EDUCACAO("Educação"),
    OUTROS("Outros");

    private String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
