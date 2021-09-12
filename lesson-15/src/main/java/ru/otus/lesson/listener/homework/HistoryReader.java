package ru.otus.lesson.listener.homework;

import ru.otus.lesson.model.Message;

import java.util.Optional;

public interface HistoryReader {

    Optional<Message> findMessageById(long id);
}
