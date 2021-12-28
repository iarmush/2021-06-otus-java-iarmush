package ru.otus.messagehandlers.response;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;

@Component("callbackResponseMessageHandler")
public class CallbackResponseMessageHandler implements RequestHandler {

    private static final Logger log = LoggerFactory.getLogger(CallbackResponseMessageHandler.class);

    @Override
    public <T extends ResultDataType> Optional<Message<T>> handle(Message<T> msg) {
        log.info("New message: {}", msg);
        try {
            var callback = msg.getCallback();
            if (callback != null) {
                callback.accept(msg.getData());
            } else {
                log.error("Callback for Id: {} not found", msg.getId());
            }
        } catch (Exception ex) {
            log.error("Message: {}", msg, ex);
        }
        return Optional.empty();
    }
}
