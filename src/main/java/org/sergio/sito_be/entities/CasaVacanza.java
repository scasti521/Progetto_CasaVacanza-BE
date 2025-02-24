package org.sergio.sito_be.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "prenotazioni"})
public class CasaVacanza implements Comparable<CasaVacanza>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Nome obbligatorio")
    @Column(nullable = false, unique = true)
    private String nome;

    @NotBlank(message = "Indirizzo obbligatorio")
    @Column(nullable = false)
    private String indirizzo;

    @NotBlank(message = "Città obbligatoria")
    @Column(nullable = false)
    private String citta;

    @NotBlank(message = "Regione obbligatoria")
    @Column
    private String regione;

    @NotBlank(message = "Descrizione obbligatoria")
    @Column(nullable = false)
    private String descrizione;

    @Column(nullable = false)
    private int postiLetto;

    @Column(nullable = false)
    private double prezzo;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean disponibile = true;

    @Column(nullable = false)
    @ElementCollection
    private List<LocalDate> dataOccupata;

    @Override
    public int compareTo(CasaVacanza other) {
        return this.nome.compareTo(other.nome);
    }

    @Column(nullable = false)
    private String immagine;

    @OneToMany (mappedBy = "casaVacanza", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Prenotazione> prenotazioni;

}