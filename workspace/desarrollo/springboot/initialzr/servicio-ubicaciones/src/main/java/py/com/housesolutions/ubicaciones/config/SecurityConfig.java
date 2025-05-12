/*
package py.com.housesolutions.ubicaciones.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF para simplificar en APIs REST
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/api/public/**").permitAll() // Endpoints públicos
                                .anyRequest().authenticated() // Todas las demás rutas requieren autenticación
                )
                .httpBasic(Customizer.withDefaults()); // Configuración básica de autenticación usando un customizer
        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Usamos BCrypt para encriptar contraseñas
    }
}
*/