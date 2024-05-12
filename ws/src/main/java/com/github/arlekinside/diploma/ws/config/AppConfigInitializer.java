package com.github.arlekinside.diploma.ws.config;

import com.github.arlekinside.diploma.logic.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigInitializer implements CommandLineRunner {

    private final UserService userService;
    private final String adminUsername;
    private final String adminPassword;

    public AppConfigInitializer(UserService userService,
                                @Value("${app.users.admin.username}") String adminUsername,
                                @Value("${app.users.admin.password}") String adminPassword) {
        this.userService = userService;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            userService.registerAdmin(adminUsername, adminPassword);
        } catch (Exception ignore) {}
    }

}
