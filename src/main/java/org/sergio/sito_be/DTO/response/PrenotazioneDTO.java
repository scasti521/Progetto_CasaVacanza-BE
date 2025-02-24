package org.sergio.sito_be.DTO.response;

import lombok.Data;
import org.sergio.sito_be.entities.CasaVacanza;
import org.sergio.sito_be.entities.Utente;

import java.time.LocalDate;
@Data
public class PrenotazioneDTO {
    private long id;
    private Utente utente;
    private CasaVacanza casa;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private double prezzoTotale;
    private int numeroPersone;

    public PrenotazioneDTO(long id, Utente utente, CasaVacanza casa, LocalDate dataInizio, LocalDate dataFine, double prezzoTotale, int numeroPersone) {
        this.id = id;
        this.utente = utente;
        this.casa = casa;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.prezzoTotale = prezzoTotale;
        this.numeroPersone = numeroPersone;
    }

}
