package ru.otus.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {

    @GetMapping("/")
    public String getClientsResource() {
        return "clients";
    }
}
