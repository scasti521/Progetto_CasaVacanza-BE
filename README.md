# Progetto_CasaVacanza-BE# Progetto_CasaVacanza-BE

Se si usa JWT + Angular, abilitare cors in spring security

```java:
http.cors(Customizer.withDefaults());
```
Con questa configurazione, Spring Security permette a tutti i metodi di richiesta di passare attraverso il filtro CORS. Questo è utile quando si utilizza JWT con Angular.

Se si vuole personalizzare il Cors, bisogna utilizzare il file CorsConfig con @Configuration

```java:
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
```

In questo modo permetto di fare richieste http a Angular che è configurato nella porta 4200.
Permetto i metodi http GET, POST, PUT, DELETE e OPTIONS. Consento tutti gli header richiesti e permetto di leggere l'header Authorization.
