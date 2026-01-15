// src/main/java/com/sintu/issue_tracker/config/SecurityConfig.java
package com.sintu.issue_tracker.config;

import com.sintu.issue_tracker.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            // 🚨 Updated CORS handling
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                // Allow H2 Console for local testing (optional)
                .requestMatchers("/h2-console/**").permitAll()
                
                // Use hasRole or hasAuthority based on how your User model returns authorities
                .requestMatchers("/api/tickets/**").hasAnyAuthority("STUDENT", "ROLE_STUDENT", "ADMIN", "ROLE_ADMIN")
                .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/api/student/**").hasAnyAuthority("STUDENT", "ROLE_STUDENT")
                
                .anyRequest().authenticated()
            )
            // Fix for H2 frames if needed
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // 🚨 PRO-TIP: During deployment, you can add your Vercel URL here
        configuration.setAllowedOriginPatterns(List.of(
            "http://localhost:5173", 
            "https://*.vercel.app", 
            "https://*.netlify.app"
        ));
        
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}