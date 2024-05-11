package com.github.arlekinside.diploma.ws.app.controller;

import com.github.arlekinside.diploma.data.repo.SUserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping("/health")
public class HealthCheckController {

    private final SUserRepo userRepo;
    private final String adminUsername;

    public HealthCheckController(SUserRepo userRepo,
                                 @Value("${app.users.admin.username}") String adminUsername) {
        this.userRepo = userRepo;
        this.adminUsername = adminUsername;
    }

    @GetMapping
    public ResponseEntity<String> getHealth() {
        var now = Instant.now();
        var admin = userRepo.findByUsername(adminUsername);
        var delay = Duration.between(now, Instant.now()).toMillis();
        if (delay > 100) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
}
