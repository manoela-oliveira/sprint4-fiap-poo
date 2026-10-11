package br.com.motiva.sistemamotiva.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.motiva.sistemamotiva.exception.EquipeInvalidaException;
import br.com.motiva.sistemamotiva.model.EquipeManutencao;
import br.com.motiva.sistemamotiva.repository.EquipeManutencaoRepository;

@Service
public class EquipeManutencaoService {

    private final EquipeManutencaoRepository repository;

    // Injeção de dependência via construtor
    public EquipeManutencaoService(EquipeManutencaoRepository repository) {
        this.repository = repository;
    }

    public List<EquipeManutencao> listarTodas() {
        return repository.findAll();
    }

    public Optional<EquipeManutencao> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public EquipeManutencao salvar(EquipeManutencao equipe) {
        // Validação de regra de negócio
        if (equipe.getQuantidadeMembros() < 2) {
            throw new EquipeInvalidaException("Equipe deve ter no mínimo 2 membros para operação.");
        }
        return repository.save(equipe);
    }

    public Optional<EquipeManutencao> atualizar(Long id, EquipeManutencao equipeAtualizada) {
        return repository.findById(id).map(equipeExistente -> {
            equipeExistente.setIdentificadorEquipe(equipeAtualizada.getIdentificadorEquipe());
            equipeExistente.setQuantidadeMembros(equipeAtualizada.getQuantidadeMembros());
            return repository.save(equipeExistente);
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