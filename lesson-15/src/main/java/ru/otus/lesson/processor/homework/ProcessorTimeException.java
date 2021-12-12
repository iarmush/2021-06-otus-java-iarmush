package ru.otus.lesson.processor.homework;

import ru.otus.lesson.model.Message;
import ru.otus.lesson.processor.Processor;

public class ProcessorTimeException implements Processor {

    private final DateTimeProvider dateTimeProvider;

    public ProcessorTimeException(final DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(final Message message) {
        if (dateTimeProvider.getLocalDateTime().getSecond() % 2 == 0) {
            throw new TimeException("Секунда чётная");
        }
        return message;
    }
}
