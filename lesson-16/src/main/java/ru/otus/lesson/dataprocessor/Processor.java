package ru.otus.lesson.dataprocessor;

import ru.otus.lesson.model.Measurement;

import java.util.List;
import java.util.Map;

public interface Processor {

    Map<String, Double> process(List<Measurement> data);
}
