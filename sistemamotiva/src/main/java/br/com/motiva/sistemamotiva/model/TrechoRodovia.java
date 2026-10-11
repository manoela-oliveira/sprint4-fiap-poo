package br.com.motiva.sistemamotiva.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "trechos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "tipo"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Autoestrada.class, name = "AUTOESTRADA"),
    @JsonSubTypes.Type(value = EstradaVicinal.class, name = "ESTRADA_VICINAL")
})
public abstract class TrechoRodovia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private IdentificacaoTrecho identificador;

    @Column(name = "nivel_vegetacao", nullable = false)
    private double nivelVegetacaoCm;

    @Column(name = "regiao_umida", nullable = false)
    private boolean regiaoUmida;

    // Construtor padrão JPA
    public TrechoRodovia() {}

    public TrechoRodovia(IdentificacaoTrecho identificador, double nivelVegetacaoCm) {
        this.identificador = identificador;
        this.setNivelVegetacaoCm(nivelVegetacaoCm);
        this.regiaoUmida = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public IdentificacaoTrecho getIdentificador() { return identificador; }
    public void setIdentificador(IdentificacaoTrecho identificador) { this.identificador = identificador; }

    public double getNivelVegetacaoCm() { return nivelVegetacaoCm; }

    public void setNivelVegetacaoCm(double nivel) {
        this.nivelVegetacaoCm = Math.max(nivel, 0.0);
    }

    public boolean isRegiaoUmida() { return regiaoUmida; }
    public void setRegiaoUmida(boolean regiaoUmida) { this.regiaoUmida = regiaoUmida; }

    public void marcarComoRegiaoUmida() { this.regiaoUmida = true; }

    public void registrarCrescimento(double taxa) {
        if (taxa > 0) {
            double taxaEfetiva = this.regiaoUmida ? taxa * 1.8 : taxa;
            this.nivelVegetacaoCm += taxaEfetiva;
        }
    }

    public void atualizarNivelVegetacao(double novoNivelCm) {
        this.nivelVegetacaoCm = Math.max(novoNivelCm, 5.0);
    }

    // Métodos polimórficos das filhas
    @Transient // Não é uma coluna no banco, é regra computada
    public abstract String getTipo();

    @Transient
    public abstract String calcularPrioridade();
}