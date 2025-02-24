package org.sergio.sito_be.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class CasaVacanzaDTO {
    private int id;
    private String nome;
    private String indirizzo;
    private String citta;
    private String regione;
    private String descrizione;
    private double prezzo;
    private int postiLetto;
    private boolean disponibile;
    private String immagine;

    public CasaVacanzaDTO(int id, String nome, String indirizzo, String citta, String regione, String descrizione, int postiLetto, double prezzo, boolean disponibile, String immagine) {
        this.id = id;
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.citta = citta;
        this.regione = regione;
        this.descrizione = descrizione;
        this.postiLetto = postiLetto;
        this.prezzo = prezzo;
        this.disponibile = disponibile;
        this.immagine=immagine;
    }
}
