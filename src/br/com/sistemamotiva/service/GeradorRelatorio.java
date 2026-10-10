package br.com.sistemamotiva.service;

import br.com.sistemamotiva.dao.RelatorioPrioridadeDAO;
import br.com.sistemamotiva.model.*;

public class GeradorRelatorio {

    public void gerarRelatorio(TrechoRodovia[] trechos) {
        System.out.println("\n---------------------------------------------------------\"");
        System.out.println("      RELATÓRIO AUTOMÁTICO DE PRIORIDADE DE ROÇADA      ");
        System.out.println("---------------------------------------------------------");

        int qtCritico = 0;
        int qtAlta = 0;
        int qtBaixa = 0;

        for (TrechoRodovia trecho : trechos) {
            String prioridade = trecho.calcularPrioridade();
            String identificador = trecho.getIdentificador().getCodigoIdentificacao();
            double kmInicial = trecho.getIdentificador().getQuilometroInicial();
            double kmFinal = trecho.getIdentificador().getQuilometroFinal();
            double nivel = trecho.getNivelVegetacaoCm();

            System.out.printf("\nTrecho: %s (KM %.1f até %.1f) | Nível: %.1fcm | Prioridade: %s\n",
                    identificador, kmInicial, kmFinal, nivel, prioridade);

            switch (prioridade) {
                case "CRITICA" -> {
                    qtCritico++;
                    System.out.println("-> Recomendação: Roçada Mecanizada Imediata.");
                }
                case "ALTA" -> {
                    qtAlta++;
                    System.out.println("-> Recomendação: Pulverização Preventiva Química.");
                }
                default -> {
                    qtBaixa++;
                    System.out.println("-> Recomendação: Monitoramento Preventivo.");
                }
            }
        }

        System.out.println("\n=----------------------------------------------------------");
        System.out.printf("Total Crítico: %d | Total Alto: %d | Total Baixo: %d\n", qtCritico, qtAlta, qtBaixa);

        // Persistência no Banco via DAO
        String resumo = String.format("Varredura de %d trechos executada.", trechos.length);
        RelatorioPrioridadeDAO dao = new RelatorioPrioridadeDAO();
        dao.salvarRelatorio(qtCritico, qtAlta, qtBaixa, resumo);
    }

    public void processarLeiturasIoT(MonitoravelViaIoT[] sensores) {
        System.out.println("\n--- PROCESSANDO LEITURAS DE TELEMETRIA IoT ---");
        for (MonitoravelViaIoT sensor : sensores) {
            double leitura = sensor.transmitirDadosSensor();
            System.out.printf("Dados processados com sucesso. Leitura capturada: %.1fcm\n", leitura);
        }
    }
}