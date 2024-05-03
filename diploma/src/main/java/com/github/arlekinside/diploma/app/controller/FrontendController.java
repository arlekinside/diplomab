package com.github.arlekinside.diploma.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

    @GetMapping(value = {"/", "/{x:[\\w\\-]+}", "/{x:^(?!api$).*$}/*/*/{y:[\\w\\-]+}"})
    public String getFrontend(){
        return "index.html";
    }

}
