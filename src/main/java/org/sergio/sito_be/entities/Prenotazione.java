package org.sergio.sito_be.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utente_nome")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "casa_vacanza_nome")
    private CasaVacanza casaVacanza;

    @NotBlank(message = "Data di check-in obbligatoria")
    @Column(nullable = false)
    private LocalDate dataInizio;

    @NotBlank(message = "Data di check-out obbligatoria")
    @Column(nullable = false)
    private LocalDate dataFine;

    @Column(nullable = false)
    private double prezzoTotale;

    @Column(nullable = false)
    private int numeroPersone;

    @PrePersist
    private void calcolaPrezzoTotale() {
        // Calcola il prezzo totale della prenotazione, moltiplicando il prezzo giornaliero della casa vacanza per il numero di giorni di permanenza
        prezzoTotale = casaVacanza.getPrezzo() * (dataFine.getDayOfYear() - dataInizio.getDayOfYear());
    }
}
