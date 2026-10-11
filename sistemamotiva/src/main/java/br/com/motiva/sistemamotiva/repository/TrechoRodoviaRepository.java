package br.com.motiva.sistemamotiva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.motiva.sistemamotiva.model.TrechoRodovia;

@Repository
public interface TrechoRodoviaRepository extends JpaRepository<TrechoRodovia, Long> {

    // Derived Query
    List<TrechoRodovia> findByNivelVegetacaoCmGreaterThanEqual(Double minimo);
    List<TrechoRodovia> findByIdentificadorCodigoIdentificacao(String codigo);
    
    // Filtrar por tipo
    @Query("SELECT t FROM TrechoRodovia t WHERE TYPE(t) = :tipo")
    List<TrechoRodovia> findByTipoClasse(@Param("tipo") Class<? extends TrechoRodovia> tipo);
}