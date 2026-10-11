package br.com.motiva.sistemamotiva.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.motiva.sistemamotiva.model.RelatorioPrioridade;

@Repository
public interface RelatorioPrioridadeRepository extends JpaRepository<RelatorioPrioridade, Long> {

    // Derived Query
    List<RelatorioPrioridade> findByDataGeracaoBetween(LocalDateTime inicio, LocalDateTime fim);
}