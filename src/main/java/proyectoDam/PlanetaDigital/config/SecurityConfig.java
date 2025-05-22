package proyectoDam.PlanetaDigital.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)  // desactiva la proteccion contra ataques Cross-Site Request Forgery
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());  //permite acceso libre a todos los endpoints

        return http.build();
    }

    // funcion para encriptar contraseña y para verificar la contraseña en la sesion
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
