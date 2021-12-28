package ru.otus.config;

import org.springframework.beans.factory.annotation.Qualifier;
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
public class FrontendMsClientConfig {

    @Bean
    public HandlersStore frontendHandlersStore(@Qualifier("callbackResponseMessageHandler") RequestHandler callbackMessageHandler) {
        HandlersStoreImpl handlersStore = new HandlersStoreImpl();
        handlersStore.addHandler(MessageType.SAVE_CLIENT, callbackMessageHandler);
        handlersStore.addHandler(MessageType.EDIT_CLIENT, callbackMessageHandler);
        return handlersStore;
    }

    @Bean
    public MsClient frontendMsClient(MessageSystem messageSystem,
        @Qualifier("frontendHandlersStore") HandlersStore handlersStore) {
        MsClientImpl frontendMsClient = new MsClientImpl("frontendMsClient", messageSystem, handlersStore);
        messageSystem.addClient(frontendMsClient);
        return frontendMsClient;
    }
}
