package org.sergio.sito_be.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Classe di configurazione della sicurezza dell'applicazione.
 * Qui vengono definiti i permessi di accesso alle API e la gestione degli utenti.
 */
@Configuration // Indica che questa classe è una configurazione di Spring
public class DemoSecurityConfig {

    /**
     * Configura la catena dei filtri di sicurezza di Spring Security.
     * Definisce quali endpoint sono accessibili e a chi.
     *
     * @param http l'oggetto HttpSecurity usato per configurare la sicurezza
     * @return il SecurityFilterChain configurato
     * @throws Exception se si verifica un errore nella configurazione
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(configurer -> configurer
                        // 🌟 ROTTE UTENTE 🌟
                        // Permettiamo a chiunque di creare un nuovo utente o fare login (senza autenticazione)
                        .requestMatchers(HttpMethod.POST, "/api/utente/crea").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/utente/login").permitAll()

                        // Solo gli amministratori possono visualizzare tutti gli utenti
                        .requestMatchers(HttpMethod.GET, "/api/utente/all").hasRole("ADMIN")

                        // Solo un utente autenticato con ruolo USER può aggiornare il proprio profilo
                        .requestMatchers(HttpMethod.POST, "/api/utente/update").hasRole("USER")

                        // Solo un amministratore può eliminare un utente
                        .requestMatchers(HttpMethod.DELETE, "/api/utente/delete/{id}").hasRole("ADMIN")

                        // 🌟 ROTTE PRENOTAZIONE 🌟
                        // Solo un utente con ruolo USER può creare una prenotazione
                        .requestMatchers(HttpMethod.POST, "/api/prenotazione/crea").hasRole("USER")

                        // Solo un amministratore può eliminare una prenotazione
                        .requestMatchers(HttpMethod.DELETE, "/api/prenotazione/delete/{id}").hasRole("ADMIN")

                        // Solo un manager può visualizzare tutte le prenotazioni
                        .requestMatchers(HttpMethod.GET, "/api/prenotazione/all").hasRole("MANAGER")

                        // Solo un utente può aggiornare una prenotazione
                        .requestMatchers(HttpMethod.PUT, "/api/prenotazione/update").hasRole("USER")

                        // 🌟 ROTTE CASA VACANZA 🌟
                        // Solo un manager può creare e aggiornare una casa vacanza
                        .requestMatchers(HttpMethod.POST, "/api/casaVacanza/crea").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.POST, "/api/casaVacanza/update").hasRole("MANAGER")

                        // Solo un amministratore può eliminare una casa vacanza
                        .requestMatchers(HttpMethod.POST, "/api/casaVacanza/delete/{id}").hasRole("ADMIN")

                        // Tutti possono visualizzare le case vacanza disponibili
                        .requestMatchers(HttpMethod.GET, "/api/casaVacanza/all").permitAll()

                        // 🌟 QUALSIASI ALTRA RICHIESTA 🌟
                        // Qualsiasi altra richiesta non specificata richiede autenticazione
                        .anyRequest().authenticated()
                );

        // Abilita l'autenticazione di base (utente e password con popup del browser o Postman)
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

}