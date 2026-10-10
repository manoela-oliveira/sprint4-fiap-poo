package br.com.sistemamotiva.model;

public abstract class IntervencaoOperacional {
    private Long id;
    private String descricao;
    private double custoEstimado;

    public IntervencaoOperacional() {}

    public IntervencaoOperacional(String descricao, double custoEstimado) {
        this.descricao = descricao;
        this.custoEstimado = custoEstimado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getCustoEstimado() { return custoEstimado; }
    public void setCustoEstimado(double custoEstimado) { this.custoEstimado = custoEstimado; }

    public abstract String getTipo();
    public abstract void executarServico(TrechoRodovia trechoAlvo);

    @Override
    public String toString() {
        return String.format("Intervencao[id=%d, tipo=%s, desc=%s, custo=R$%.2f]", id, getTipo(), descricao, custoEstimado);
    }
}