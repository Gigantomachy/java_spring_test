package com.exercises.hellospring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    // order matters - spring security evaluates top down and stops at the first match
    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
        // NOTE: /api/authors would fall into anyRequest().authenticated() here

        http.authorizeHttpRequests((authorize) -> authorize
                                                .requestMatchers(HttpMethod.GET, "/api/books/**").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/books/**").authenticated()
                                                .requestMatchers(HttpMethod.PUT, "/api/books/**").authenticated()
                                                .requestMatchers(HttpMethod.DELETE, "/api/books/**").authenticated()
                                                .requestMatchers("/h2-console/**").permitAll().anyRequest().authenticated());
        
        http.httpBasic(Customizer.withDefaults());
        http.csrf((csrf) -> csrf.disable()); // we are building a stateless API

        // changes headers to X-Frame-Options: SAMEORIGIN
        // h2-console is built with iframes, this allows iframes if parent page is from same domain and port
        http.headers(headers -> headers.frameOptions(f -> f.sameOrigin()));
        
        return http.build();
    }

    @Bean
    public UserDetailsService getUserDetailsService() {
        // Spring Security requires passwords to be stored with an encoding prefix that tells it how the password was hashed / encoded
        // noop = plain text
        UserDetails user = User.builder().username("user").password("{noop}password123").roles("USER").build();
        UserDetails admin = User.builder().username("admin").password("{noop}admin123").roles("USER", "ADMIN").build();

        return new InMemoryUserDetailsManager(user, admin);
    }



}
