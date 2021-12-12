package ru.otus.lesson.dataprocessor;

import ru.otus.lesson.model.Measurement;

import java.util.List;

public interface Loader {

    List<Measurement> load();
}
