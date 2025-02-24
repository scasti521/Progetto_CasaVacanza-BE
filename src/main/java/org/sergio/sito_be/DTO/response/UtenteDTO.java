package org.sergio.sito_be.DTO.response;

import lombok.Data;
import org.sergio.sito_be.entities.Utente;

@Data
public class UtenteDTO {
    private long id;
    private String nome;
    private String cognome;
    private String username;

    public UtenteDTO(Long id,String nome, String cognome, String username) {
        this.id=id;
        this.nome=nome;
        this.cognome=cognome;
        this.username=username;
    }
}
