package br.com.motiva.sistemamotiva.model;

import br.com.motiva.sistemamotiva.exception.EquipeInvalidaException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "equipes")
public class EquipeManutencao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identificador", nullable = false, length = 50)
    private String identificadorEquipe;

    @Column(name = "quantidade_membros", nullable = false)
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

    @Override
    public String toString() {
        return String.format("Equipe[id=%d, identificador=%s, membros=%d]", id, identificadorEquipe, quantidadeMembros);
    }
}