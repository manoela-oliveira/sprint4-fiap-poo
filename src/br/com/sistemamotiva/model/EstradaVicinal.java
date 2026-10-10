package br.com.sistemamotiva.model;

public class EstradaVicinal extends TrechoRodovia {
    private boolean isPavimentada;

    public EstradaVicinal(IdentificacaoTrecho identificador, double nivelVegetacao, boolean isPavimentada) {
        super(identificador, nivelVegetacao);
        this.isPavimentada = isPavimentada;
    }

    public boolean isPavimentada() {
        return isPavimentada;
    }

    @Override
    public String calcularPrioridade() {
        if (getNivelVegetacaoCm() > 40) {
            return "CRITICA";
        }
        if (!isPavimentada && getNivelVegetacaoCm() > 20) {
            return "ALTA";
        }
        return "BAIXA";
    }

    @Override
    public String getTipo() {
        return "ESTRADA_VICINAL";
    }
}