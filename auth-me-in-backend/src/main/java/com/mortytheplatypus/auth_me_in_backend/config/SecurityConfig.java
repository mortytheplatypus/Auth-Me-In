package com.mortytheplatypus.auth_me_in_backend.config;

import com.mortytheplatypus.auth_me_in_backend.filter.JwtAuthFilter;
import com.mortytheplatypus.auth_me_in_backend.model.Role;
import com.mortytheplatypus.auth_me_in_backend.service.UserDetailsServiceImpl;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@Data
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthFilter jwtAuthFilter;

    private static final String[] WHITELIST = {
            "/public/**",
            "/auth/**"
    };

    /**
     * Configures the security filter chain.
     *
     * @param http the HttpSecurity object used to configure web based security for specific http requests.
     * @return the configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) 
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(
                req -> req.requestMatchers(WHITELIST)
                    .permitAll()
                    .requestMatchers("/admin/**")
                    .hasAuthority(Role.ADMIN.name())
                    .anyRequest()
                    .authenticated()
            )
            .userDetailsService(userDetailsService)
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    /**
     * Configures CORS (Cross-Origin Resource Sharing) settings for the application.
     *
     * @return A CorsConfigurationSource bean that defines CORS policies.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); 
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Configures the password encoder to be used for encoding passwords.
     *
     * @return the PasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the authentication manager to be used for authentication.
     *
     * @param configuration the AuthenticationConfiguration object.
     * @return the configured AuthenticationManager.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(@NotNull AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
