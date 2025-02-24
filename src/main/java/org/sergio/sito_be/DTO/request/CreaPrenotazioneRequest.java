package org.sergio.sito_be.DTO.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreaPrenotazioneRequest {
    private long utenteId;
    private long casaVacanzaId;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private int numeroPersone;
}
