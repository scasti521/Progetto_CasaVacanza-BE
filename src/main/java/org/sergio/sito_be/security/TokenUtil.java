package org.sergio.sito_be.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.sergio.sito_be.entities.Ruolo;
import org.sergio.sito_be.entities.Utente;
import org.sergio.sito_be.service.def.UtenteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenUtil {

    private final UtenteService utenteService;

    @Value("${auto.jwt.key}")
    private String key;

    private SecretKey generaChiave(){
        return Keys.hmacShaKeyFor(key.getBytes());
    }

    public String generaToken(Utente u){
        long id = u.getId();
        String username = u.getUsername();
        String nome = u.getNome();
        String cognome = u.getCognome();
        Ruolo ruolo = u.getRuolo();

        long durata = 1000L * 60 * 60 * 8;
        return Jwts.builder().claims()
                .add("id", id)
                .add("nome", nome.toUpperCase())
                .add("cognome", cognome)
                .add("ruolo", ruolo.ordinal())
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + durata))
                .and()
                .signWith(generaChiave())
                .compact();
    }

    private Claims prendiClaimsDalToken(String token){
        JwtParser parser = Jwts.parser()
                .verifyWith(generaChiave())
                .build();
        return parser.parseSignedClaims(token)
                .getPayload();
    }

    public String getSubject(String token){
        return prendiClaimsDalToken(token).getSubject();
    }

    public Utente getUtenteFromToken(String token){
        String username = getSubject(token);
        return utenteService.findByUsername(username);
    }



}
