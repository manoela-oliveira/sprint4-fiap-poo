package br.com.motiva.sistemamotiva.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.motiva.sistemamotiva.model.IntervencaoOperacional;
import br.com.motiva.sistemamotiva.repository.IntervencaoOperacionalRepository;

@Service
public class IntervencaoOperacionalService {

    private final IntervencaoOperacionalRepository repository;

    public IntervencaoOperacionalService(IntervencaoOperacionalRepository repository) {
        this.repository = repository;
    }

    public List<IntervencaoOperacional> listarTodas() {
        return repository.findAll();
    }

    public Optional<IntervencaoOperacional> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public IntervencaoOperacional salvar(IntervencaoOperacional intervencao) {
        if (intervencao.getCustoEstimado() <= 0) {
            throw new IllegalArgumentException("O custo estimado da intervenção deve ser maior que zero.");
        }
        return repository.save(intervencao);
    }

    public Optional<IntervencaoOperacional> atualizar(Long id, IntervencaoOperacional atualizada) {
        return repository.findById(id).map(existente -> {
            existente.setDescricao(atualizada.getDescricao());
            existente.setCustoEstimado(atualizada.getCustoEstimado());
            return repository.save(existente);
        });
    }

    public boolean deletar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}