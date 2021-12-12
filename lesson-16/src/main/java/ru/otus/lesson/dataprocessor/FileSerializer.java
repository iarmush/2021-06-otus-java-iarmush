package ru.otus.lesson.dataprocessor;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        try (final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            final JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            for (final Map.Entry<String, Double> stringDoubleEntry : data.entrySet()) {
                objectBuilder.add(stringDoubleEntry.getKey(), stringDoubleEntry.getValue());
            }

            bufferedWriter.write(objectBuilder.build().toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
