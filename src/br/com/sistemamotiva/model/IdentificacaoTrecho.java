package br.com.sistemamotiva.model;

/* Esta classe foi criada para evitar a adição excessiva de parâmetros na classe TrechoRodovia
  Intuito: respeitar a regra de Clean Code mantendo no máximo 3 parâmetros, garantindo maior 
  legibilidade do codigo e facilitando a manutenção a longo prazo.

  Para definir/identificar um trecho rodoviários precisamos saber:
  - Código de identificação
  - Onde começa (Km)
  - Onde termina (Km) */

import br.com.sistemamotiva.exception.TrechoInvalidoException;

public class IdentificacaoTrecho {

    private final String codigoIdentificacao;
    private final double quilometroInicial;
    private final double quilometroFinal;

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

    public double getQuilometroInicial() {
        return quilometroInicial;
    }

    public double getQuilometroFinal() {
        return quilometroFinal;
    }

    @Override
    public String toString() {
        return String.format("%s (KM %.1f ao %.1f)", codigoIdentificacao, quilometroInicial, quilometroFinal);
    }
}