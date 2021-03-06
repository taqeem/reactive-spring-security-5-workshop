package com.example.oauth2loginclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityWebFilterChain configure(ServerHttpSecurity http) throws Exception {
        http
                .authorizeExchange()
                .pathMatchers("/", "/books/**", "/users/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .oauth2Login()
                .and()
                .formLogin()
                .and()
                .oauth2Client();
        return http.build();
    }

    @Bean
    MapReactiveUserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("user")
                .password("secret")
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(userDetails);
    }
}
