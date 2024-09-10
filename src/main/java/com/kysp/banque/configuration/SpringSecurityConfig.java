package com.kysp.banque.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.kysp.banque.repository.CompteRepository;
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final CompteRepository compteRepository;
    public SpringSecurityConfig(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }
    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http
    //         .csrf().disable()
    //         .authorizeHttpRequests(auth -> {
    //             auth.requestMatchers("/banque/ouvrir", "/banque/comptes", "/banque/comptes/{accno}")
    //                 .hasRole("ADMIN");
    //             auth.requestMatchers("/banque/ouvrir", "/banque/comptes", "/banque/comptes/{accno}", "/banque/modifier","/banque/supprimer/{accno}","/banque/depot/{accno}","/banque/retrait/{accno}","/banque/transfert")
    //                 .hasAnyRole("USER", "ADMIN");
    //             auth.anyRequest().authenticated();
    //         })
    //         .httpBasic();
    //     return http.build();
    // }

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeHttpRequests(auth -> {
            
            auth.requestMatchers("/banque/modifier", "/banque/supprimer/{accno}")
                .hasRole("ADMIN");

            
            auth.requestMatchers("/banque/ouvrir", "/banque/comptes", "/banque/comptes/{accno}", 
                "/banque/depot/{accno}", "/banque/retrait/{accno}", "/banque/transfert")
                .hasAnyRole("USER", "ADMIN");
            
            
            auth.anyRequest().authenticated();
        })
        .httpBasic();
    return http.build();
}

    


@Bean
public UserDetailsService userDetailsService() {
    return username -> {
        com.kysp.banque.models.Compte compte = compteRepository.findByUsername(username);
        if (compte == null) {
            throw new UsernameNotFoundException("User not found");
        }
        System.out.println("User found: " + compte.getUsername() + " with role: " + compte.getRole());
        return org.springframework.security.core.userdetails.User
            .withUsername(compte.getUsername())
            .password(compte.getPassword())
            .roles(compte.getRole().replace("ROLE_", ""))
            .build();
    };
}




    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
