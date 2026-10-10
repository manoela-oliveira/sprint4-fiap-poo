package br.com.sistemamotiva.model;

public class AutoestradaSensorizada extends Autoestrada implements MonitoravelViaIoT {

    public AutoestradaSensorizada(IdentificacaoTrecho identificador, double nivelVegetacao, int quantidadeFaixas) {
        super(identificador, nivelVegetacao, quantidadeFaixas);
    }

    @Override
    public double transmitirDadosSensor() {
        System.out.println("[IoT Telemetria] Obtendo dados de sensoriamento remoto ativo...");
        return getNivelVegetacaoCm();
    }
}
