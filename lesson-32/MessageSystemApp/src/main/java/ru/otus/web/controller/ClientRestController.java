package ru.otus.web.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.db.DBServiceClient;

@RestController
public class ClientRestController {

    private final DBServiceClient clientService;

    public ClientRestController(DBServiceClient clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/api/clients")
    public List<Client> getClients() {
        return clientService.findAll();
    }
}
