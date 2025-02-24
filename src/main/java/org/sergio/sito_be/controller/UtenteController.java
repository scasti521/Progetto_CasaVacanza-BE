package org.sergio.sito_be.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.sergio.sito_be.DTO.request.CreaUtenteRequest;
import org.sergio.sito_be.DTO.request.LoginRequest;
import org.sergio.sito_be.DTO.request.UpdateUtente;
import org.sergio.sito_be.DTO.response.CasaVacanzaDTO;
import org.sergio.sito_be.DTO.response.UtenteDTO;
import org.sergio.sito_be.entities.Utente;
import org.sergio.sito_be.security.TokenUtil;
import org.sergio.sito_be.service.def.UtenteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/utente")
@AllArgsConstructor
public class UtenteController {

    private UtenteService utenteService;

    private final TokenUtil tokenUtil;

    @PostMapping("/crea")
    public ResponseEntity<UtenteDTO> creaUtente( @Valid @RequestBody CreaUtenteRequest request) {
        try {
            System.out.println("Ricevuto:" + request.getNome() + " " + request.getCognome() + " " + request.getUsername() + " " + request.getPassword());
            utenteService.aggiungiUtente(request);
            System.out.println("Utente 2 creato");
            return ResponseEntity.status(HttpStatus.OK).header("Utente creato", "token").build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login( @Valid @RequestBody LoginRequest request) {
        Utente utente= utenteService.Login(request);
        //System.out.println(utente);
        String token= tokenUtil.generaToken(utente);
        //System.out.println(ResponseEntity.status(HttpStatus.OK).header("Authorization", token).build());
        return ResponseEntity.status(HttpStatus.OK).header("Authorization", token).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<UtenteDTO>> findAll(){
        List<Utente> utente = utenteService.findAll();
        List<UtenteDTO> utenteDTOS = utente.stream()
                .map(utente1 -> new UtenteDTO(utente1.getId(), utente1.getNome(), utente1.getCognome(), utente1.getUsername()))
                .sorted(Comparator.comparing(utente2-> utente2.getNome().toLowerCase())) //inserisco ordinamento confrontando il nome non contanto minuscole e maiuscole
                .toList();
        return ResponseEntity.ok(utenteDTOS);
    }

    //modificare questo metodo, implementare il serviceJPA per il metodo update, cosi funziona ma aggiunge una nuova utente
    @PostMapping("/update")
    public ResponseEntity<UtenteDTO> updateUtente(@Valid @RequestBody UpdateUtente request) {
        try {
            System.out.println("Dati utente ricevuti dalla richiesta: " + request.getNome() + " " + request.getCognome() + " " + request.getUsername() + " " + request.getPassword());
            utenteService.modificaUtente(request);
            return  ResponseEntity.status(HttpStatus.OK).header("Utente aggiornato", "token").build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
            utenteService.deleteUtente(id);
            return  ResponseEntity.status(HttpStatus.OK).header("Utente eliminato", "token").build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

