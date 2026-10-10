package br.com.sistemamotiva.model;

public class Manutencao {
    private String data;
    private EquipeManutencao responsavel;
    private TrechoRodovia localizador;
    private IntervencaoOperacional servico; 

    public Manutencao(String data, EquipeManutencao responsavel, TrechoRodovia localizador, IntervencaoOperacional servico) {
        this.setData(data);
        this.responsavel = responsavel;
        this.localizador = localizador;
        this.setServico(servico);
    }

    public String getData() {
        return this.data;
    }

    private void setData(String data) {
    
        if (data != null && !data.trim().isEmpty()) {
            this.data = data;
        } else {
            throw new IllegalArgumentException("Erro! Por favor, defina uma data válida.");
        }
    }

    public EquipeManutencao getResponsavel() {
        return this.responsavel;
    }

    public TrechoRodovia getLocalizador() {
        return this.localizador;
    }

    public IntervencaoOperacional getServico() {
        return this.servico;
    }

    private void setServico(IntervencaoOperacional servicoRecomendado) {
        if (servicoRecomendado != null) {
            this.servico = servicoRecomendado;
        } else {
            throw new IllegalArgumentException("Erro! O serviço de intervenção não pode ser nulo.");
        }
    }

    public void executarManutencao() {
        System.out.println("\n--- INICIANDO EXECUÇÃO DE ORDEM DE SERVIÇO ---");
        System.out.println("Data Planejada: " + this.data);
        this.responsavel.realizarTrabalho(this.servico, this.localizador);
    }
}