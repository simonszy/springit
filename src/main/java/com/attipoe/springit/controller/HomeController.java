package com.attipoe.springit.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

    @GetMapping(value = "/", consumes = "application/json", produces = "application/json")
    public String home() {
        return "index";
    }
}
