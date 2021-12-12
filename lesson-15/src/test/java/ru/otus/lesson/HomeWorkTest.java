package ru.otus.lesson;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.lesson.model.Message;
import ru.otus.lesson.processor.homework.ProcessorTimeException;
import ru.otus.lesson.processor.homework.TimeException;

import java.time.LocalDateTime;

class HomeWorkTest {

    @Test
    @DisplayName("Проверка исключения в чётную секунду")
    void failTest() {
        //given
        final var message = new Message.Builder(1L).field1("field1").build();

        //when
        final var processorWithException = new ProcessorTimeException(() -> LocalDateTime
                .of(2021, 1, 1, 1, 1, 2));

        //then
        Assertions.assertThrows(TimeException.class, () -> processorWithException.process(message));
    }
}