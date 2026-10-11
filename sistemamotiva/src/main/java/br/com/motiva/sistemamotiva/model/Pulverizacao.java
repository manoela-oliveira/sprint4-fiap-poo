package br.com.motiva.sistemamotiva.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PULVERIZACAO")
public class Pulverizacao extends IntervencaoOperacional {

    public Pulverizacao() {
        super("Pulverização Química Preventiva", 850.0);
    }

    public Pulverizacao(String descricao, double custo) {
        super(descricao, custo);
    }

    @Override
    public String getTipo() {
        return "PULVERIZACAO";
    }

    @Override
    public void executarServico(TrechoRodovia trechoAlvo) {
        trechoAlvo.atualizarNivelVegetacao(8.0);
    }
}