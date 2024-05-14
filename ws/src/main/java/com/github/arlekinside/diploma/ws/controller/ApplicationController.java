package com.github.arlekinside.diploma.ws.controller;

import com.github.arlekinside.diploma.ws.Utils;
import com.github.arlekinside.diploma.ws.dto.AppDataDTO;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class ApplicationController {

    @GetMapping("/data")
    public AppDataDTO getAppData(Authentication auth) {
        var user = Utils.getUser(auth);
        return null;
    }

}
