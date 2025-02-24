package org.sergio.sito_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.sergio.sito_be.DTO.request.CreaPrenotazioneRequest;
import org.sergio.sito_be.DTO.request.UpdatePrenotazioneRequest;
import org.sergio.sito_be.entities.CasaVacanza;
import org.sergio.sito_be.entities.Prenotazione;
import org.sergio.sito_be.entities.Utente;
import org.sergio.sito_be.repository.CasaVacanzaRepo;
import org.sergio.sito_be.repository.PrenotazioneRepo;
import org.sergio.sito_be.repository.UtenteRepo;
import org.sergio.sito_be.service.def.PrenotazioneService;
import org.sergio.sito_be.service.def.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrenotazioneServiceJPA implements PrenotazioneService{
    @Autowired
    private CasaVacanzaRepo casaVacanzaRepo;
    @Autowired
    private PrenotazioneRepo prenotazioneRepo;
    @Autowired
    private UtenteRepo utenteRepo;

    @Override
    public Prenotazione creaPrenotazione(CreaPrenotazioneRequest request) {
        //Controllo la disponibilità della casaVacanza
        CasaVacanza casaVacanza = casaVacanzaRepo.findById(request.getCasaVacanzaId()).orElseThrow(() -> new RuntimeException("CasaVacanza not found"));
        List<LocalDate> disponibilità = casaVacanza.getDataOccupata();
        for(LocalDate data = request.getDataInizio(); data.isBefore(request.getDataFine()); data = data.plusDays(1)){
            if(disponibilità.contains(data)){
                throw new RuntimeException("CasaVacanza non disponibile");
            }
            casaVacanza.getDataOccupata().add(data);
        }
        casaVacanzaRepo.save(casaVacanza);

        Prenotazione p = new Prenotazione();
        p.setUtente(utenteRepo.findById(request.getUtenteId()).orElseThrow(() -> new RuntimeException("Utente not found")));
        p.setCasaVacanza(casaVacanzaRepo.findById(request.getCasaVacanzaId()).get());
        p.setDataInizio(request.getDataInizio());
        p.setDataFine(request.getDataFine());
        p.setNumeroPersone(request.getNumeroPersone());
        prenotazioneRepo.save(p);
        return p;
    }

    @Override
    public void deletePrenotazione(long id) {
/*        System.out.println("id: " + id);
        Optional<Prenotazione> prenotazione = prenotazioneRepo.findById(id);
        if (prenotazione.isPresent()) {
            prenotazioneRepo.deletePrenotazioneById(id);
        } else {
            throw new RuntimeException("Prenotazione not found");
        }*/
            Optional<Prenotazione> prenotazioneOpt = prenotazioneRepo.findById(id);
            if (prenotazioneOpt.isPresent()) {
                Prenotazione prenotazione = prenotazioneOpt.get();
                CasaVacanza casaVacanza = prenotazione.getCasaVacanza();

                // Rimuovi le date occupate
                for (LocalDate data = prenotazione.getDataInizio(); data.isBefore(prenotazione.getDataFine()); data = data.plusDays(1)) {
                    casaVacanza.getDataOccupata().remove(data);
                }
                casaVacanzaRepo.save(casaVacanza);

                // Elimina la prenotazione
                prenotazioneRepo.delete(prenotazione);
            } else {
                throw new RuntimeException("Prenotazione not found");
            }
    }

    @Override
    public List<Prenotazione> findAll() {
        return prenotazioneRepo.findAll();
    }

    @Override
    public Prenotazione modificaPrenotazione(UpdatePrenotazioneRequest request){
        Optional<Prenotazione> p = prenotazioneRepo.findById(request.getId());
        if (p.isEmpty()) {
            throw new RuntimeException("Prenotazione not found");
        }
        Prenotazione prenotazione = p.get();
        prenotazione.setUtente(utenteRepo.findById(request.getUtenteId()).orElseThrow(() -> new RuntimeException("Utente not found")));
        prenotazione.setCasaVacanza(casaVacanzaRepo.findById(request.getCasaVacanzaId()).orElseThrow(() -> new RuntimeException("CasaVacanza not found")));
        prenotazione.setDataInizio(request.getDataInizio());
        prenotazione.setDataFine(request.getDataFine());
        prenotazione.setNumeroPersone(request.getNumeroPersone());
        return prenotazioneRepo.save(prenotazione);
    }

}
