package ru.otus.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.web.dto.ClientDto;

@Controller
public class ClientController {

    private final DBServiceClient clientService;

    public ClientController(DBServiceClient clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public ModelAndView getUsers() {
        return getModel("clients");
    }

    @PostMapping("/clients")
    public ModelAndView createUser(@ModelAttribute("createClientDto") ClientDto clientDto) {
        clientService.save(clientDto.toClient());
        return getModel("clients");
    }

    @PostMapping("/clients/edit")
    public ModelAndView updateUser(@ModelAttribute("updateClientDto") ClientDto clientDto) {
        clientService.save(clientDto.toClient());
        return getModel("redirect:/");
    }

    private ModelAndView getModel(String viewName) {
        ModelAndView model = new ModelAndView(viewName);
        model.addObject("createClientDto", new ClientDto());
        model.addObject("updateClientDto", new ClientDto());
        model.addObject("clients", clientService.findAll());
        return model;
    }
}
