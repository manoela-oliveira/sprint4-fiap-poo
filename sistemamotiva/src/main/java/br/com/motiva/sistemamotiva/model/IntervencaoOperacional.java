package br.com.motiva.sistemamotiva.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "intervencoes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "tipo"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = RocadaMecanizada.class, name = "ROCADA"),
    @JsonSubTypes.Type(value = Pulverizacao.class, name = "PULVERIZACAO")
})
public abstract class IntervencaoOperacional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "custo_estimado", nullable = false)
    private double custoEstimado;

    // Construtor padrão JPA
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

    @Transient
    public abstract String getTipo();

    public abstract void executarServico(TrechoRodovia trechoAlvo);
}