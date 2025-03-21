package org.sergio.sito_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.sergio.sito_be.DTO.request.CreaUtenteRequest;
import org.sergio.sito_be.DTO.request.LoginRequest;
import org.sergio.sito_be.DTO.request.UpdateUtente;
import org.sergio.sito_be.entities.CasaVacanza;
import org.sergio.sito_be.entities.Ruolo;
import org.sergio.sito_be.entities.Utente;
import org.sergio.sito_be.repository.UtenteRepo;
import org.sergio.sito_be.service.def.UtenteService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtenteServiceJPA implements UtenteService {

    private final UtenteRepo utenteRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void aggiungiUtente(CreaUtenteRequest request) {
        Utente u = new Utente();
        u.setNome(request.getNome());
        u.setCognome(request.getCognome());
        u.setUsername(request.getUsername());
        u.setPassword(passwordEncoder.encode(request.getPassword()));
        u.setRuolo(Ruolo.ROLE_USER);
        utenteRepo.save(u);
    }

    @Override
    public Utente Login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        if (username == null || password == null) {
            return null;
        }
        return utenteRepo.findByUsernameAndPassword(username, password).orElse(null);
    }

    @Override
    public Utente findByUsername(String username){
        Optional<Utente> utente = this.utenteRepo.findByUsername(username);
        return utente.orElse(null);
    }

    @Override
    public List<Utente> findAll() {
        return utenteRepo.findAll().stream().sorted().toList();
    }

    @Override
    public Utente modificaUtente(UpdateUtente utente){
        Utente u= findByUsername(utente.getUsername());
        u.setNome(utente.getNome());
        u.setCognome(utente.getCognome());
        u.setUsername(utente.getUsername());
        u.setPassword(utente.getPassword());
        return this.utenteRepo.save(u);
    }

    @Override
    public void deleteUtente(long id){
        Utente u = this.utenteRepo.findById(id).orElse(null);
        if(u == null){
            System.out.println("Utente non trovato");
        }else{
            this.utenteRepo.delete(u);
            System.out.println("Utente eliminato con successo");
        }
    }
}
