package br.com.motiva.sistemamotiva.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.motiva.sistemamotiva.model.RelatorioPrioridade;
import br.com.motiva.sistemamotiva.model.TrechoRodovia;
import br.com.motiva.sistemamotiva.repository.RelatorioPrioridadeRepository;
import br.com.motiva.sistemamotiva.repository.TrechoRodoviaRepository;

@Service
public class RelatorioPrioridadeService {

    private final RelatorioPrioridadeRepository relatorioRepository;
    private final TrechoRodoviaRepository trechoRepository;

    public RelatorioPrioridadeService(RelatorioPrioridadeRepository relatorioRepository,
                                     TrechoRodoviaRepository trechoRepository) {
        this.relatorioRepository = relatorioRepository;
        this.trechoRepository = trechoRepository;
    }

    public List<RelatorioPrioridade> listarHistorico() {
        return relatorioRepository.findAll();
    }

    public List<RelatorioPrioridade> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return relatorioRepository.findByDataGeracaoBetween(inicio, fim);
    }

    // Motor de calculo de prioridades exigido no Requisito 3.3
    public RelatorioPrioridade gerarEPersistirRelatorio() {
        List<TrechoRodovia> trechos = trechoRepository.findAll();

        int critico = 0;
        int alta = 0;
        int baixa = 0;

        for (TrechoRodovia trecho : trechos) {
            String prioridade = trecho.calcularPrioridade();
            switch (prioridade) {
                case "CRITICA" -> critico++;
                case "ALTA" -> alta++;
                default -> baixa++;
            }
        }

        String resumo = String.format("Varredura executada em %d trechos cadastrados.", trechos.size());

        RelatorioPrioridade novoRelatorio = new RelatorioPrioridade(critico, alta, baixa, resumo);
        return relatorioRepository.save(novoRelatorio);
    }
}