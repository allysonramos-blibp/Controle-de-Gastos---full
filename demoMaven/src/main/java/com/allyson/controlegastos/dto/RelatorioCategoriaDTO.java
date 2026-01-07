package com.allyson.controlegastos.dto;

import com.allyson.controlegastos.model.Categoria;
import java.math.BigDecimal;

public class RelatorioCategoriaDTO {

    private Categoria categoria;
    private BigDecimal total;
    private Long quantidade;

    public RelatorioCategoriaDTO(Categoria categoria, BigDecimal total, Long quantidade) {
        this.categoria = categoria;
        this.total = total;
        this.quantidade = quantidade;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }
}