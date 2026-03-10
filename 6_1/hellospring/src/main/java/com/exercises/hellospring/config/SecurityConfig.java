package com.exercises.hellospring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.exercises.hellospring.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity // security at the http level
@EnableMethodSecurity // security at the method level - enables @PreAuthorize in BookService and other places
public class SecurityConfig {

    // 1. User does POST to /api/auth/login with user + pass, receives JWT
    // 2. on subsequent requests requiring auth, we use Authorization Bearer <token>
    // JWT is self contained, contains user information + expiry

    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // we don't use this SecurityFilterChain bean anywhere in our code manually
    // Spring applies this to every HTTP request under the hood
    // HTTP Request -> SecurityFilterChain -> Controller

    // order matters - spring security evaluates top down and stops at the first match
    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {

        // http.authorizeHttpRequests((authorize) -> authorize
        //                                         .requestMatchers(HttpMethod.GET, "/api/books/**").permitAll()
        //                                         .requestMatchers(HttpMethod.POST, "/api/books/**").authenticated()
        //                                         .requestMatchers(HttpMethod.PUT, "/api/books/**").authenticated()
        //                                         .requestMatchers(HttpMethod.DELETE, "/api/books/**").authenticated()
        //                                         .requestMatchers("/h2-console/**").permitAll().anyRequest().authenticated());

        http.authorizeHttpRequests((authorize) -> authorize
                                                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll() // needed in case we remove http.httpBasic (basic auth)
                                                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/**").hasRole("USER")
                                                .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("USER")
                                                .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                                                .requestMatchers("/h2-console/**").permitAll().anyRequest().authenticated());
        
        
        // Note: basic auth (user + pass to POST/PUT/DELETE /api/books etc...) is still enabled - should probably get rid of this later
        // BookIntegrationTest still uses basic auth - leave this in for now
        http.httpBasic(Customizer.withDefaults()); // handles basic auth ( curl ... -u user:password ... )
        http.csrf((csrf) -> csrf.disable()); // we are building a stateless API

        // changes headers to X-Frame-Options: SAMEORIGIN
        // h2-console is built with iframes, this allows iframes if parent page is from same domain and port
        http.headers(headers -> headers.frameOptions(f -> f.sameOrigin()));

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // UsernamePasswordAuthenticationFilter is Spring's default security filter for form-based logins
        // even if we remove .formLogin() we usually keep this as a reference point
        // UsernamePasswordAuthenticationFilter doesn't do anything if jwt authenticates first
        // Request -> JwtAuthenticationFilter -> BasicAuthenticationFilter -> UsernamePasswordAuthenticationFilter -> ...
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }

    // Used in AuthController and JwtAuthenticationFilter to obtain user information
    @Bean
    public UserDetailsService getUserDetailsService() {
        // Spring Security requires passwords to be stored with an encoding prefix that tells it how the password was hashed / encoded
        // noop = plain text
        UserDetails user = User.builder().username("user").password("{noop}password123").roles("USER").build();
        UserDetails admin = User.builder().username("admin").password("{noop}admin123").roles("USER", "ADMIN").build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    // Used in AuthController to confirm password and username match, user isn't invalid/expired, etc...
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
