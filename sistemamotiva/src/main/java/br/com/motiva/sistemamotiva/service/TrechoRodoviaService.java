package br.com.motiva.sistemamotiva.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.motiva.sistemamotiva.exception.TrechoInvalidoException;
import br.com.motiva.sistemamotiva.model.Autoestrada;
import br.com.motiva.sistemamotiva.model.EstradaVicinal;
import br.com.motiva.sistemamotiva.model.TrechoRodovia;
import br.com.motiva.sistemamotiva.repository.TrechoRodoviaRepository;

@Service
public class TrechoRodoviaService {

    private final TrechoRodoviaRepository repository;

    public TrechoRodoviaService(TrechoRodoviaRepository repository) {
        this.repository = repository;
    }

    public List<TrechoRodovia> listarTodos() {
        return repository.findAll();
    }

    public Optional<TrechoRodovia> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<TrechoRodovia> buscarPorVegetacaoMinima(Double minimo) {
        return repository.findByNivelVegetacaoCmGreaterThanEqual(minimo);
    }

    public TrechoRodovia salvar(TrechoRodovia trecho) {
        if (trecho.getNivelVegetacaoCm() < 0) {
            throw new TrechoInvalidoException("O nível de vegetação não pode ser negativo.");
        }
        return repository.save(trecho);
    }

    public Optional<TrechoRodovia> atualizar(Long id, TrechoRodovia trechoAtualizado) {
        return repository.findById(id).map(existente -> {
            existente.setNivelVegetacaoCm(trechoAtualizado.getNivelVegetacaoCm());
            existente.setRegiaoUmida(trechoAtualizado.isRegiaoUmida());

            if (existente instanceof Autoestrada autoExistente && trechoAtualizado instanceof Autoestrada autoNovo) {
                autoExistente.setQuantidadeFaixas(autoNovo.getQuantidadeFaixas());
            } else if (existente instanceof EstradaVicinal vicExistente && trechoAtualizado instanceof EstradaVicinal vicNova) {
                vicExistente.setPavimentada(vicNova.isPavimentada());
            }

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