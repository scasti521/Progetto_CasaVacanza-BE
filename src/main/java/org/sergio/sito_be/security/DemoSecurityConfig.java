package org.sergio.sito_be.security;

import org.sergio.sito_be.security.jwt.AuthEntryPointJwt;
import org.sergio.sito_be.security.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class DemoSecurityConfig {

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery("SELECT username, password, enabled=1 FROM utente WHERE username = ?");
        userDetailsManager.setAuthoritiesByUsernameQuery("SELECT u.username, UPPER(u.ruolo) FROM utente u WHERE u.username = ?");
        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) //Abilito il CORS, ma non lo configuro. Per configurarlo bisogna usare il file CorsConfig con il @Configuration
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/api/utente/login", "/api/utente/crea").permitAll()
                                .requestMatchers("api/utente/validate").permitAll()

                                // Permessi per CasaVacanzaController
                                .requestMatchers(HttpMethod.GET, "/api/casaVacanza/all").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/casaVacanza/crea").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/casaVacanza/**").hasRole("ADMIN")

                                // Permessi per PrenotazioneController
                                .requestMatchers(HttpMethod.GET, "/api/prenotazione/all").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/prenotazione/crea").hasRole("USER")
                                .requestMatchers(HttpMethod.DELETE, "/api/prenotazione/**").hasRole("ADMIN")

                                // Permessi per UtenteController
                                .requestMatchers(HttpMethod.GET, "/api/utente/all").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/utente/update").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.DELETE, "/api/utente/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}