package com.github.arlekinside.diploma.ws.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static com.github.arlekinside.diploma.data.SecurityRoles.*;


@Configuration
public class RequestSecurityConfig {

    @Bean
    protected SecurityFilterChain requestSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers("/admin/**", "/health", "/history/stats", "/users/stats")
                                .hasRole(ADMIN.name())
                )
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(
                                HttpMethod.GET,
                                "/", "/home", "/history", "/users",
                                "/mf/**", "/goal/**", "/saving/**"
                        ).hasAnyRole(USER.name(), ADMIN.name())
                )
                .formLogin(login -> login.loginPage("/login")
                        .loginProcessingUrl("/users/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error=true")
                )
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                )
                .authorizeHttpRequests(
                        auth -> auth.anyRequest().permitAll()
                );
        return http.build();
    }
}
