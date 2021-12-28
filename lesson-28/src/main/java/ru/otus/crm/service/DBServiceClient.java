package ru.otus.crm.service;

import java.util.List;
import ru.otus.crm.model.Client;

public interface DBServiceClient {

    void save(Client client);

    List<Client> findAll();

}
