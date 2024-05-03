package com.github.arlekinside.diploma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableAsync
public class DiplomaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiplomaApplication.class, args);
    }

}
