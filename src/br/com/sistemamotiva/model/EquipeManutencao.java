package br.com.sistemamotiva.model;

import br.com.sistemamotiva.exception.EquipeInvalidaException;

public class EquipeManutencao {

    private Long id;
    private String identificadorEquipe;
    private int quantidadeMembros;

    public EquipeManutencao() {}

    public EquipeManutencao(String identificadorEquipe, int quantidadeMembros) {
        setIdentificadorEquipe(identificadorEquipe);
        setQuantidadeMembros(quantidadeMembros);
    }

    public EquipeManutencao(Long id, String identificadorEquipe, int quantidadeMembros) {
        this(identificadorEquipe, quantidadeMembros);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificadorEquipe() {
        return identificadorEquipe;
    }

    public void setIdentificadorEquipe(String identificador) {
        if (identificador == null || identificador.trim().isEmpty()) {
            throw new EquipeInvalidaException("O identificador da equipe é obrigatório.");
        }
        this.identificadorEquipe = identificador;
    }

    public int getQuantidadeMembros() {
        return quantidadeMembros;
    }

    public void setQuantidadeMembros(int quantidadeMembros) {
        if (quantidadeMembros < 2) {
            throw new EquipeInvalidaException("Por segurança operacional, a equipe deve conter no mínimo 2 membros.");
        }
        this.quantidadeMembros = quantidadeMembros;
    }

    /* Executa a ordem de serviço no trecho indicado.
      Nomes baseados em papéis: servicoSolicitado e trechoDesignado.*/
    public void realizarTrabalho(IntervencaoOperacional servicoSolicitado, TrechoRodovia trechoDesignado) {
        System.out.println("Equipe '" + this.identificadorEquipe + "' mobilizada para execução de serviço.");
        servicoSolicitado.executarServico(trechoDesignado);
    }

    @Override
    public String toString() {
        return String.format("Equipe[id=%d, identificador=%s, membros=%d]", id, identificadorEquipe, quantidadeMembros);
    }
}