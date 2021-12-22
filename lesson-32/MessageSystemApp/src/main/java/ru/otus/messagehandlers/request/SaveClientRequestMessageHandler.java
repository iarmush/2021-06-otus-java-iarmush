package ru.otus.messagehandlers.request;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.db.DBServiceClient;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;

@Component("saveClientRequestMessageHandler")
public class SaveClientRequestMessageHandler implements RequestHandler {

    private static final Logger log = LoggerFactory.getLogger(SaveClientRequestMessageHandler.class);

    private final DBServiceClient dbServiceClient;

    public SaveClientRequestMessageHandler(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ResultDataType> Optional<Message<T>> handle(Message<T> msg) {
        log.info("Handled message: {}", msg);
        Client client = (Client) msg.getData();
        Client savedClient = dbServiceClient.save(client);
        return Optional.of(MessageBuilder.buildReplyMessage(msg, (T) savedClient));
    }
}
