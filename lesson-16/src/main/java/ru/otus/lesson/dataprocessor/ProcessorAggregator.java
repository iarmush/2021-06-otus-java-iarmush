package ru.otus.lesson.dataprocessor;

import ru.otus.lesson.model.Measurement;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value
        return data.stream()
                .collect(Collectors.toMap(
                        Measurement::getName,
                        Measurement::getValue,
                        Double::sum,
                        TreeMap::new
                ));
    }
}
