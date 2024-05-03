package com.github.arlekinside.diploma.app.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class RequestSecurityConfig {

    @Bean
    protected SecurityFilterChain requestSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers("/admin", "/admin/**", "/health", "/history/stats", "/users/stats")
                                .hasRole(SecurityRoles.ADMIN.name())
                )
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(HttpMethod.POST, "/sql")
                                .hasAnyRole(SecurityRoles.USER.name(), SecurityRoles.ADMIN.name())
                )
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(HttpMethod.GET, "/", "/home", "/history", "/users")
                                .hasAnyRole(SecurityRoles.USER.name(), SecurityRoles.ADMIN.name())
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
