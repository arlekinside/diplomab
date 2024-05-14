package com.github.arlekinside.diploma.ws.config.security;

import org.springframework.beans.factory.annotation.Value;
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

    private final String uiUrl;

    public RequestSecurityConfig(@Value("${app.ui.url}") String uiUrl) {
        this.uiUrl = uiUrl;
    }


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
                                "/", "/home", "/history", "/users", "/app/data",
                                "/mf/**", "/goal/**", "/saving/**"
                        ).hasAnyRole(USER.name(), ADMIN.name())
                )
                .formLogin(login -> login.loginPage("%s/login".formatted(uiUrl))
                        .loginProcessingUrl("/users/login")
                        .defaultSuccessUrl(uiUrl)
                        .failureUrl("%s/login?error=true".formatted(uiUrl))
                )
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl(uiUrl)
                )
                .authorizeHttpRequests(
                        auth -> auth.anyRequest().permitAll()
                );
        return http.build();
    }
}
