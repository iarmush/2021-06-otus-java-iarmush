package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageSystemImpl;

@Configuration
public class MessageSystemConfig {

    @Bean(destroyMethod = "dispose")
    public MessageSystem messageSystem() {
        MessageSystemImpl messageSystem = new MessageSystemImpl();
        messageSystem.start();
        return messageSystem;
    }
}
