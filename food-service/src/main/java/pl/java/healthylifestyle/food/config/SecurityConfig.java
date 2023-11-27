package pl.java.healthylifestyle.food.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String[] SWAGGER_WHITELIST_URLS = new String[]{
            "/foods/swagger-resources/**", "/foods/webjars/**", "/foods/swagger-ui/**", "/foods/v3/api-docs/**"};

    private final KeycloakRoleConverter keycloakRoleConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry ->
                        registry.requestMatchers(SWAGGER_WHITELIST_URLS).permitAll()
                                .anyRequest().authenticated())
                .oauth2ResourceServer(configurer -> configurer.jwt(jwtConfigurer ->
                        jwtConfigurer.jwtAuthenticationConverter(keycloakRoleConverter)))
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(STATELESS))
                .build();
    }
}
