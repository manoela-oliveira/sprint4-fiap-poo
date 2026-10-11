package br.com.motiva.sistemamotiva.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("AUTOESTRADA")
public class Autoestrada extends TrechoRodovia {

    @Column(name = "quantidade_faixas")
    private int quantidadeFaixas;

    public Autoestrada() {
        super();
    }

    public Autoestrada(IdentificacaoTrecho identificador, double nivelVegetacao, int quantidadeFaixas) {
        super(identificador, nivelVegetacao);
        this.setQuantidadeFaixas(quantidadeFaixas);
    }

    public int getQuantidadeFaixas() {
        return quantidadeFaixas;
    }

    public void setQuantidadeFaixas(int quantidadeFaixas) {
        if (quantidadeFaixas >= 1) {
            this.quantidadeFaixas = quantidadeFaixas;
        } else {
            throw new IllegalArgumentException("Erro! É obrigatório que haja pelo menos uma faixa no trecho rodoviário.");
        }
    }

    @Override
    public String calcularPrioridade() {
        if (getNivelVegetacaoCm() > 30) {
            return "CRITICA";
        }
        if (getNivelVegetacaoCm() > 20 && quantidadeFaixas > 3) {
            return "ALTA";
        }
        return "BAIXA";
    }

    @Override
    public String getTipo() {
        return "AUTOESTRADA";
    }
}