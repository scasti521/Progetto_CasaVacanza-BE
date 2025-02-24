//Definisco il service, che contiene la logica di business e utilizza la repository per interagire con il database.

package org.sergio.sito_be.service.def;

import org.sergio.sito_be.DTO.request.CreaUtenteRequest;
import org.sergio.sito_be.DTO.request.LoginRequest;
import org.sergio.sito_be.DTO.request.UpdateUtente;
import org.sergio.sito_be.entities.Utente;

import java.util.List;

public interface UtenteService {
    public void aggiungiUtente(CreaUtenteRequest request);

    public Utente Login(LoginRequest loginRequest);

    Utente findByUsername(String username);

    List<Utente> findAll();

    Utente modificaUtente(UpdateUtente utente);

    void deleteUtente(long id);

}
