package com.scots.openbanking.openbankingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

    // Catch all routes except those starting with /api or files like .js, .css
    @GetMapping(value = "/{path:^(?!api|static|.*\\..*).*$}")
    public String redirect() {
        // This will serve index.html so React can do its routing
        return "forward:/index.html";
    }
}
