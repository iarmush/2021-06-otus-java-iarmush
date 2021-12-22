package ru.otus.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;

@Configuration
public class DbMsClientConfig {

    @Bean
    public HandlersStore dbHandlersStore(@Qualifier("saveClientRequestMessageHandler") RequestHandler saveClientMessageHandler,
        @Qualifier("editClientRequestMessageHandler") RequestHandler getClientsMessageHandler) {
        HandlersStoreImpl handlersStore = new HandlersStoreImpl();
        handlersStore.addHandler(MessageType.SAVE_CLIENT, saveClientMessageHandler);
        handlersStore.addHandler(MessageType.EDIT_CLIENT, getClientsMessageHandler);
        return handlersStore;
    }

    @Bean
    public MsClient dbMsClient(@Value("${msClient.name}") String msClientName, MessageSystem messageSystem,
        @Qualifier("dbHandlersStore") HandlersStore handlersStore) {
        MsClientImpl dbMsClient = new MsClientImpl(msClientName, messageSystem, handlersStore);
        messageSystem.addClient(dbMsClient);
        return dbMsClient;
    }
}
