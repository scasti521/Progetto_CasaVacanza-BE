//mi vado a definire la repository, classe che ci fornisce i metodi per interagire con il database, che infine sarà usata
// dal controller per gestire le richieste http

package org.sergio.sito_be.repository;

import org.sergio.sito_be.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepo  extends JpaRepository<Utente, Long> {
    public Utente findById(int Id);
    Optional<Utente> findByUsernameAndPassword(String username, String password);

    Optional<Utente> findByUsername(String username);

}
