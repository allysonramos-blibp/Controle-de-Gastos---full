package com.allyson.controlegastos.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Fatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int mes;
    private int ano;

    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private StatusFatura status;

    /* GETTERS E SETTERS */

    public Long getId() { return id; }

    public int getMes() { return mes; }
    public void setMes(int mes) { this.mes = mes; }

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public StatusFatura getStatus() { return status; }
    public void setStatus(StatusFatura status) { this.status = status; }
}
