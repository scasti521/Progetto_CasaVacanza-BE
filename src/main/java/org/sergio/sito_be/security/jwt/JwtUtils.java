package org.sergio.sito_be.security.jwt;

import io.jsonwebtoken.*; // Libreria per gestire i JWT (JSON Web Token)
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

/**
 * Classe di utilità per la gestione dei JWT (JSON Web Token).
 * Questa classe fornisce metodi per:
 * - Estrarre un token JWT dall'header della richiesta HTTP
 * - Generare un token JWT a partire dal nome utente
 * - Ottenere il nome utente dal token JWT
 * - Validare il token JWT
 */
@Component // Indica che questa classe è un componente Spring e può essere iniettata in altre parti dell'applicazione
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    // Il segreto utilizzato per firmare il token JWT, viene letto dal file di configurazione application.properties
    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    // Durata del token in millisecondi, anch'esso caricato dal file di configurazione
    @Getter // Genera automaticamente il metodo get per ottenere il valore di jwtExpirationMs
    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * Estrae il token JWT dall'header della richiesta HTTP.
     * @param request La richiesta HTTP
     * @return Il token JWT se presente, altrimenti null
     */
    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization"); // Estrae l'header Authorization
        logger.debug("Authorization Header: {}", bearerToken); // Logga il valore dell'header Authorization
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) { // Verifica se il token è presente e ha il prefisso corretto
            return bearerToken.substring(7); // Rimuove il prefisso "Bearer " e restituisce solo il token
        }
        return null;
    }

    /**
     * Genera un token JWT a partire dal nome utente dell'utente autenticato.
     * @param userDetails Oggetto che rappresenta l'utente autenticato
     * @return Il token JWT generato
     */
    public String generateTokenFromUsername(UserDetails userDetails) {
        String username = userDetails.getUsername(); // Ottiene il nome utente
        String ruolo = userDetails.getAuthorities().iterator().next().getAuthority(); // Ottiene il ruolo dell'utente
        return Jwts.builder()
                .claims()
                .add("ruolo", ruolo) // Aggiunge il nome utente come claim del token
                .subject(username) // Imposta il nome utente come soggetto del token
                .issuedAt(new Date()) // Imposta la data di emissione
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Imposta la scadenza del token
                .and()
                .signWith(key()) // Firma il token con la chiave segreta
                .compact(); // Crea e restituisce il token
    }

    /**
     * Estrae il nome utente dal token JWT.
     * @param token Il token JWT
     * @return Il nome utente contenuto nel token
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key()) // Verifica il token con la chiave segreta
                .build().parseSignedClaims(token) // Effettua il parsing del token
                .getPayload().getSubject(); // Estrae il soggetto (nome utente) dal payload del token
    }

    /**
     * Restituisce la chiave segreta usata per firmare e verificare i token JWT.
     * @return La chiave segreta
     */
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)); // Decodifica la chiave segreta dalla configurazione e la restituisce
    }

    /**
     * Valida un token JWT controllando se è ben formato, non scaduto e firmato correttamente.
     * @param authToken Il token JWT da validare
     * @return true se il token è valido, false altrimenti
     */
    public boolean validateJwtToken(String authToken) {
        try {
            System.out.println("Validate"); // Stampa un messaggio di debug
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken); // Verifica e analizza il token
            return true; // Se non ci sono eccezioni, il token è valido
        } catch (MalformedJwtException e) { // Token non valido
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) { // Token scaduto
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) { // Tipo di token non supportato
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) { // Token vuoto o non valido
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false; // Se c'è un'eccezione, il token non è valido
    }
}