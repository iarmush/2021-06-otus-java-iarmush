package ru.otus.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.crm.service.db.DBServiceClient;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    @MockBean
    private DBServiceClient clientService;

    @Autowired
    private MockMvc mvc;

    @Test
    void getClientsResource() throws Exception {
        mvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    void getClients() throws Exception {
        mvc.perform(get("/api/clients"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"));
    }
}
