package ru.otus.lesson.processor.homework;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.Month;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.otus.lesson.model.Message;
import ru.otus.lesson.model.ObjectForMessage;

class ProcessorTimeExceptionTest {

    @Mock
    DateTimeProvider dateTimeProvider;

    @InjectMocks
    ProcessorTimeException processorTimeException;

    private final Message message = new Message(0L, "field1", "field2", "field3", "field4",
        "field5", "field6", "field7", "field8",
        "field9", "field10", "field11", "field12", new ObjectForMessage());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcess() {
        when(dateTimeProvider.getLocalDateTime()).thenReturn(LocalDateTime.of(2021, Month.NOVEMBER, 13, 23, 13, 1));

        Message result = processorTimeException.process(message);
        Assertions.assertEquals(message, result);
    }

    @Test
    void testProcessTimeException() {
        when(dateTimeProvider.getLocalDateTime()).thenReturn(LocalDateTime.of(2021, Month.NOVEMBER, 13, 23, 13, 0));

        TimeException timeException = Assertions.assertThrows(TimeException.class, () -> processorTimeException.process(message));

        Assertions.assertEquals("Секунда чётная", timeException.getMessage());
    }
}
