package com.github.arlekinside.diploma.ws.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReactController {

    @GetMapping(value = {"/", "/{x:[\\w\\-]+}", "/{x:^(?!api$).*$}/*/*/{y:[\\w\\-]+}"})
    public String getReact(){
        return "index.html";
    }

}
