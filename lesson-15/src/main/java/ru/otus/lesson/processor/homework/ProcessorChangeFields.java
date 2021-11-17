package ru.otus.lesson.processor.homework;

import ru.otus.lesson.model.Message;
import ru.otus.lesson.processor.Processor;

public class ProcessorChangeFields implements Processor {

    @Override
    public Message process(Message message) {
        var field11 = message.getField11();
        var field12 = message.getField12();
        return message.toBuilder().field11(field12).field12(field11).build();
    }
}
