package org.sergio.sito_be.repository;

import org.sergio.sito_be.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PrenotazioneRepo  extends JpaRepository<Prenotazione, Long> {

    Optional<Prenotazione> findByUtenteId(long utenteId);
    Optional<Prenotazione> findByCasaVacanzaId(long casaVacanzaId);
    Optional<Prenotazione> findByDataInizio(LocalDate dataInizio);
    Optional<Prenotazione> findByDataFine(LocalDate dataFine);

    @Modifying
    @Transactional
    @Query("DELETE FROM Prenotazione p WHERE p.id = :id")
    void deletePrenotazioneById(Long id);

    @Override
    Optional<Prenotazione> findById(Long id);
}
