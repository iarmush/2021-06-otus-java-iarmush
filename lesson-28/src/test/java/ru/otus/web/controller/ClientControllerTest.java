package ru.otus.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Role;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.web.dto.ClientDto;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    @MockBean
    private DBServiceClient clientService;

    @Autowired
    private MockMvc mvc;

    @Test
    void getClients() throws Exception {
        mvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    void postClients() throws Exception {
        mvc.perform(post("/clients")
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .flashAttr("createClientDto", new ClientDto("1", Role.USER)))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/html;charset=UTF-8"));

        verify(clientService).save(any(Client.class));
    }

    @Test
    void postClientsEdit() throws Exception {
        mvc.perform(post("/clients/edit", new ClientDto("1", Role.USER)))
            .andExpect(status().is(302));

        verify(clientService).save(any(Client.class));
    }
}