package org.sergio.sito_be.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "prenotazioni"})
public class Utente implements Comparable<Utente>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    @PrePersist
    protected void onCreate() {
        if (ruolo == null) {
            ruolo = Ruolo.user; // Imposta il ruolo di default
        }
    }

    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Prenotazione> prenotazioni;

    @Override
    public int compareTo(Utente other) {
        return this.nome.compareTo(other.nome);
    }

}
