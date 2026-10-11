package br.com.motiva.sistemamotiva.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "relatorios")
public class RelatorioPrioridade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_geracao", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataGeracao;

    @Column(name = "qt_critico", nullable = false)
    private int qtCritico;

    @Column(name = "qt_alta", nullable = false)
    private int qtAlta;

    @Column(name = "qt_baixa", nullable = false)
    private int qtBaixa;

    @Column(name = "resumo", nullable = false, length = 1000)
    private String resumo;

    // Construtor JPA
    public RelatorioPrioridade() {}

    public RelatorioPrioridade(int qtCritico, int qtAlta, int qtBaixa, String resumo) {
        this.dataGeracao = LocalDateTime.now();
        this.qtCritico = qtCritico;
        this.qtAlta = qtAlta;
        this.qtBaixa = qtBaixa;
        this.resumo = resumo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDataGeracao() { return dataGeracao; }
    public void setDataGeracao(LocalDateTime dataGeracao) { this.dataGeracao = dataGeracao; }

    public int getQtCritico() { return qtCritico; }
    public void setQtCritico(int qtCritico) { this.qtCritico = qtCritico; }

    public int getQtAlta() { return qtAlta; }
    public void setQtAlta(int qtAlta) { this.qtAlta = qtAlta; }

    public int getQtBaixa() { return qtBaixa; }
    public void setQtBaixa(int qtBaixa) { this.qtBaixa = qtBaixa; }

    public String getResumo() { return resumo; }
    public void setResumo(String resumo) { this.resumo = resumo; }
}