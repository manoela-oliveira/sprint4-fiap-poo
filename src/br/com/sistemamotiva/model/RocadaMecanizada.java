package br.com.sistemamotiva.model;

public class RocadaMecanizada extends IntervencaoOperacional {

    public RocadaMecanizada() {
        super("Roçada Mecânica Pesada", 1500.0);
    }

    public RocadaMecanizada(String descricao, double custo) {
        super(descricao, custo);
    }

    @Override
    public String getTipo() {
        return "ROCADA";
    }

    @Override
    public void executarServico(TrechoRodovia trechoAlvo) {
        trechoAlvo.atualizarNivelVegetacao(5.0);
        System.out.println("[Serviço] Roçada Mecanizada concluída no trecho: " 
            + trechoAlvo.getIdentificador().getCodigoIdentificacao());
    }
}