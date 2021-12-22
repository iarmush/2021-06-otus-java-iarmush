package ru.otus.crm.service.db;

import java.util.List;
import ru.otus.crm.model.Client;

public interface DBServiceClient {

    Client save(Client client);

    List<Client> findAll();

}
