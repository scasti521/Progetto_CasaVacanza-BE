package org.sergio.sito_be.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.sergio.sito_be.DTO.request.CreaPrenotazioneRequest;
import org.sergio.sito_be.DTO.request.UpdatePrenotazioneRequest;
import org.sergio.sito_be.DTO.response.PrenotazioneDTO;
import org.sergio.sito_be.entities.Prenotazione;
import org.sergio.sito_be.service.def.PrenotazioneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;


@RestController
@RequestMapping("/api/prenotazione")
@AllArgsConstructor
public class PrenotazioneController {

    public PrenotazioneService prenotazioneService;

    @PostMapping("/crea")
    public ResponseEntity<Prenotazione> creaPrenotazione(@Valid @RequestBody CreaPrenotazioneRequest request){

        try {
            Prenotazione prenotazioneCreata = prenotazioneService.creaPrenotazione(request);
            return ResponseEntity.ok(prenotazioneCreata);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePrenotazione(@PathVariable long id) {
        try {
            prenotazioneService.deletePrenotazione(id);
            return  ResponseEntity.status(HttpStatus.OK).header("Prenotazione eliminata", "token").build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Prenotazione>> findAll() {
        try {
            List<Prenotazione> prenotazioni = prenotazioneService.findAll();
            return ResponseEntity.ok(prenotazioni);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Prenotazione> updatePrenotazione(@Valid @RequestBody UpdatePrenotazioneRequest request){
        try{
            Prenotazione p = prenotazioneService.modificaPrenotazione(request);
            return ResponseEntity.ok(p);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
