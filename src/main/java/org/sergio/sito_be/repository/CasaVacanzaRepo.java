package org.sergio.sito_be.repository;

import org.sergio.sito_be.entities.CasaVacanza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CasaVacanzaRepo  extends JpaRepository<CasaVacanza, Long> {

    public CasaVacanza findById(int Id);
    Optional<CasaVacanza> findByNome(String nome);
    Optional<CasaVacanza> findByCitta(String citta);
    Optional<CasaVacanza> findByRegione(String regione);
}
