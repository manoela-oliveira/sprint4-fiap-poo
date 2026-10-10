package br.com.sistemamotiva.model;

/*Esta classe foi criada para atender à sugestão de teste unitário, permitindo a captura dos dados
  IoT de forma isolada, sem depender  das regras de negócio ou dos atributos físicos da classe
  TrechoRodovia, implementando a interface MonitoravelViaIoT de forma pura. */

public class MockTrechoSensorizado implements MonitoravelViaIoT {
    private double leituraSimuladaCm;

    public MockTrechoSensorizado(double leituraSimuladaCm) {
        this.leituraSimuladaCm = leituraSimuladaCm;
    }

    @Override
    public double transmitirDadosSensor() {
        return this.leituraSimuladaCm;
    }

    public void simularCrescimento(double valorCm) {
        this.leituraSimuladaCm += valorCm;
    }
}