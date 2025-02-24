package org.sergio.sito_be.service.def;

import org.sergio.sito_be.DTO.request.CreaPrenotazioneRequest;
import org.sergio.sito_be.DTO.request.UpdatePrenotazioneRequest;
import org.sergio.sito_be.entities.Prenotazione;

import java.util.List;

public interface PrenotazioneService {
    //metodo per creazre una prenotazione, prendo in ingresso  un oggetto di tipo CreaPrenotazioneRequest
    public Prenotazione creaPrenotazione(CreaPrenotazioneRequest request);
    //public Prenotazione updatePrenotazione();
    public void deletePrenotazione(long id);

    public Prenotazione modificaPrenotazione(UpdatePrenotazioneRequest prenotazione);

    List<Prenotazione> findAll();

}
