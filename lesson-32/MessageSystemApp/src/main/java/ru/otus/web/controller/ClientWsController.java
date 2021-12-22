package ru.otus.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.frontend.FrontendService;

@Controller
public class ClientWsController {

    private static final Logger log = LoggerFactory.getLogger(ClientWsController.class);

    private final FrontendService frontendService;
    private final SimpMessagingTemplate template;

    public ClientWsController(FrontendService frontendService, SimpMessagingTemplate template) {
        this.frontendService = frontendService;
        this.template = template;
    }

    @MessageMapping("/edit")
    public void editClient(Client client) {
        log.info("Client to edit: {}", client);
        frontendService.editClient(client, this::sendClientToEdit);
    }

    @MessageMapping("/save")
    public void saveClient(Client client) {
        log.info("Client to save: {}", client);
        frontendService.saveClient(client, this::sendClientToSave);
    }

    private void sendClientToEdit(Client savedClient) {
        template.convertAndSend("/topic/client/response/edit", savedClient);
    }

    private void sendClientToSave(Client savedClient) {
        template.convertAndSend("/topic/client/response/save", savedClient);
    }
}
