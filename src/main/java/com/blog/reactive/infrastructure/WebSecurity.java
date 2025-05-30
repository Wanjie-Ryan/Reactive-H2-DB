package com.blog.reactive.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
// enables spring security for webflux
public class WebSecurity {

    // this can be used anywhere else in the app just by asking for it - no need to manually create it everytime.
    @Bean
    SecurityWebFilterChain httpSecurityFilterChain(ServerHttpSecurity http) {
        return http.authorizeExchange(exchanges -> exchanges.pathMatchers(HttpMethod.POST, "/users").permitAll().pathMatchers(HttpMethod.POST, "/login")
                .permitAll().anyExchange().authenticated()).csrf(ServerHttpSecurity.CsrfSpec::disable).build();
//        CSRF --> Cross Site Request Forgery. Type of attack where malicious actor tricks a user into making unwanted requests to a different site or app on which the user is authenticated. CSRF targets state changing ops (Post, Put, delete requests).
        // The reason to disable CSRF protection might be that the app uses JWT or cookies for stateless auth, whereCSRF is less a concern.

        // passwordEncoder


    }

    // creates a new instance of bcrypt password encoder
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
