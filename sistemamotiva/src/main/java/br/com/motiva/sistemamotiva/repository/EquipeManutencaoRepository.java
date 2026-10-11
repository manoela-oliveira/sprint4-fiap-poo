package br.com.motiva.sistemamotiva.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.motiva.sistemamotiva.model.EquipeManutencao;

@Repository
public interface EquipeManutencaoRepository extends JpaRepository<EquipeManutencao, Long> {

    // Derived Query: o Spring Data gera o SQL automaticamente baseado no nome do método
    Optional<EquipeManutencao> findByIdentificadorEquipe(String identificadorEquipe);
}