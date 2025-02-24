package org.sergio.sito_be.DTO.request;

import lombok.Data;
import lombok.NonNull;
import org.sergio.sito_be.entities.Ruolo;

@Data
public class CreaUtenteRequest {
    private String nome;

    private String cognome;

    private String username;

    private String password;


/*    public CreaUtenteRequest() {
    }

    public CreaUtenteRequest(String username, String password, String nome, String cognome) {
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }*/

}
