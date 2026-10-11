package br.com.motiva.sistemamotiva.model;

import br.com.motiva.sistemamotiva.exception.TrechoInvalidoException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class IdentificacaoTrecho {

    @Column(name = "codigo", nullable = false, length = 50)
    private String codigoIdentificacao;

    @Column(name = "km_inicial", nullable = false)
    private double quilometroInicial;

    @Column(name = "km_final", nullable = false)
    private double quilometroFinal;

    // Construtor padrão JPA
    public IdentificacaoTrecho() {}

    public IdentificacaoTrecho(String codigoIdentificacao, double quilometroInicial, double quilometroFinal) {
        if (codigoIdentificacao == null || codigoIdentificacao.trim().isEmpty()) {
            throw new TrechoInvalidoException("Código de identificação do trecho não pode ser nulo ou vazio.");
        }
        if (quilometroInicial < 0 || quilometroFinal < 0) {
            throw new TrechoInvalidoException("A quilometragem não pode ser negativa.");
        }
        if (quilometroFinal < quilometroInicial) {
            throw new TrechoInvalidoException("O quilômetro final não pode ser menor que o quilômetro inicial.");
        }

        this.codigoIdentificacao = codigoIdentificacao;
        this.quilometroInicial = quilometroInicial;
        this.quilometroFinal = quilometroFinal;
    }

    public String getCodigoIdentificacao() {
        return codigoIdentificacao;
    }

    public void setCodigoIdentificacao(String codigoIdentificacao) {
        this.codigoIdentificacao = codigoIdentificacao;
    }

    public double getQuilometroInicial() {
        return quilometroInicial;
    }

    public void setQuilometroInicial(double quilometroInicial) {
        this.quilometroInicial = quilometroInicial;
    }

    public double getQuilometroFinal() {
        return quilometroFinal;
    }

    public void setQuilometroFinal(double quilometroFinal) {
        this.quilometroFinal = quilometroFinal;
    }

    @Override
    public String toString() {
        return String.format("%s (KM %.1f ao %.1f)", codigoIdentificacao, quilometroInicial, quilometroFinal);
    }
}