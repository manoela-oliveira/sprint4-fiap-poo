package br.com.motiva.sistemamotiva.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ESTRADA_VICINAL")
public class EstradaVicinal extends TrechoRodovia {

    @Column(name = "is_pavimentada")
    private boolean isPavimentada;

    public EstradaVicinal() {
        super();
    }

    public EstradaVicinal(IdentificacaoTrecho identificador, double nivelVegetacao, boolean isPavimentada) {
        super(identificador, nivelVegetacao);
        this.isPavimentada = isPavimentada;
    }

    public boolean isPavimentada() {
        return isPavimentada;
    }

    public void setPavimentada(boolean isPavimentada) {
        this.isPavimentada = isPavimentada;
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