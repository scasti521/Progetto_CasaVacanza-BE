package org.sergio.sito_be.DTO.request;

//RIchiesta di creazione di una casa vacanza, contiene i dati necessari per creare una casa vacanza

import lombok.Data;

@Data
public class CreaCasaVacanzaRequest {
    private String nome;
    private String indirizzo;
    private String citta;
    private String regione;
    private String descrizione;
    private int postiLetto;
    private double prezzo;
    private String immagine;

}
