package com.github.arlekinside.diploma.ws.controller;

import com.github.arlekinside.diploma.logic.service.AccountingService;
import com.github.arlekinside.diploma.ws.Utils;
import com.github.arlekinside.diploma.ws.dto.DashboardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final AccountingService accountingService;

    @GetMapping
    public DashboardDTO dashboard(Authentication auth) {
        var user = Utils.getUser(auth);

        return accountingService.buildDashboard(user.getId());
    }

}
