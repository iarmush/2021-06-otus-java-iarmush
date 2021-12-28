package ru.otus.lesson.handler;

import ru.otus.lesson.model.Message;
import ru.otus.lesson.listener.Listener;

public interface Handler {
    Message handle(Message msg);

    void addListener(Listener listener);
    void removeListener(Listener listener);
}
