package br.com.sistemamotiva.model;

public abstract class TrechoRodovia {
    private Long id;
    private double nivelVegetacaoCm;
    private boolean regiaoUmida;
    private IdentificacaoTrecho identificador;

    public TrechoRodovia(IdentificacaoTrecho identificador, double nivelVegetacaoCm) {
        this.identificador = identificador;
        this.setNivelVegetacaoCm(nivelVegetacaoCm);
        this.regiaoUmida = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public abstract String getTipo();
    public abstract String calcularPrioridade();

    public double getNivelVegetacaoCm() { return nivelVegetacaoCm; }

    public void setNivelVegetacaoCm(double nivel) {
        this.nivelVegetacaoCm = Math.max(nivel, 0.0);
    }

    public boolean isRegiaoUmida() { return regiaoUmida; }
    public void setRegiaoUmida(boolean regiaoUmida) { this.regiaoUmida = regiaoUmida; }

    public IdentificacaoTrecho getIdentificador() { return identificador; }
    public void setIdentificador(IdentificacaoTrecho identificador) { this.identificador = identificador; }

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

    public void exibirInformacoes() {
        String clima = this.regiaoUmida ? "Úmido (Acelerado)" : "Padrão";
        System.out.println("\nTrecho: " + this.identificador.getCodigoIdentificacao() 
            + " | Extensão: " + identificador.getQuilometroInicial() + "km a " + identificador.getQuilometroFinal() + "km"
            + " | Ambiente: " + clima);
        System.out.println("Vegetação: " + this.nivelVegetacaoCm + "cm | Prioridade: " + this.calcularPrioridade());
    }
}