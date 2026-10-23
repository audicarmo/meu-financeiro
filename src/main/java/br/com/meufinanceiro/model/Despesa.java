package br.com.meufinanceiro.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Despesa {

    private Long id;
    private String descricao;
    private BigDecimal valor;
    private String categoria;
    private LocalDate data;
    private String formaPagamento;

    public Despesa() {
    }

    public Despesa(String descricao,
                   BigDecimal valor,
                   String categoria,
                   LocalDate data,
                   String formaPagamento) {

        this.descricao = descricao;
        this.valor = valor;
        this.categoria = categoria;
        this.data = data;
        this.formaPagamento = formaPagamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}
