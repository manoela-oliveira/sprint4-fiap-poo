package br.com.motiva.sistemamotiva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.motiva.sistemamotiva.model.IntervencaoOperacional;

@Repository
public interface IntervencaoOperacionalRepository extends JpaRepository<IntervencaoOperacional, Long> {
    
    // Derived query opcional para buscar por custo máximo
    List<IntervencaoOperacional> findByCustoEstimadoLessThanEqual(Double tetoCusto);
}