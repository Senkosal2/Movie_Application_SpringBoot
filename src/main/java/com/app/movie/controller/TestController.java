package com.app.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TestController {
    
    @GetMapping
    public String test() {
        return "redirect:/swagger-ui/index.html";
    }
    
}
