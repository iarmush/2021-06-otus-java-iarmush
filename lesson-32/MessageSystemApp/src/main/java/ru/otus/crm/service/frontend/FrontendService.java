package ru.otus.crm.service.frontend;

import ru.otus.crm.model.Client;
import ru.otus.messagesystem.client.MessageCallback;

public interface FrontendService {

    void editClient(Client client, MessageCallback<Client> clientConsumer);

    void saveClient(Client client, MessageCallback<Client> clientConsumer);
}
