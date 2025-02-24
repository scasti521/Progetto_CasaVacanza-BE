package org.sergio.sito_be.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Consente a tutti gli endpoint
                .allowedOrigins("http://localhost:4200") // Consente richieste solo da Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Metodi HTTP consentiti
                .allowedHeaders("*") // Consente tutti gli header richiesti
                .allowCredentials(true) // Necessario se usi autenticazione basata su cookie
                .exposedHeaders("Authorization"); // Consente di leggere l'header Authorization

    }
}
