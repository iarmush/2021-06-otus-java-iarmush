package ru.otus.crm.service.frontend;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.crm.model.Client;
import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageType;

@Service
public class FrontendServiceImpl implements FrontendService {

    private final String targetClientName;
    private final MsClient frontendMsClient;

    public FrontendServiceImpl(@Value("${msClient.name}") String targetClientName,
        @Qualifier("frontendMsClient") MsClient frontendMsClient) {
        this.targetClientName = targetClientName;
        this.frontendMsClient = frontendMsClient;
    }

    @Override
    public void editClient(Client client, MessageCallback<Client> clientConsumer) {
        Message<Client> message = frontendMsClient.produceMessage(targetClientName, client, MessageType.EDIT_CLIENT, clientConsumer);
        frontendMsClient.sendMessage(message);
    }

    @Override
    public void saveClient(Client client, MessageCallback<Client> clientConsumer) {
        Message<Client> message = frontendMsClient.produceMessage(targetClientName, client, MessageType.SAVE_CLIENT, clientConsumer);
        frontendMsClient.sendMessage(message);
    }
}
