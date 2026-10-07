package com.intranetstemdo.intranet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("dev")
public class H2ConsoleSecurityConfig {

    @Bean
    @Order(1)
    SecurityFilterChain h2ConsoleChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/h2-console/**")
            .authorizeHttpRequests(auth -> auth.anyRequest().hasRole("ADMIN"))
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
