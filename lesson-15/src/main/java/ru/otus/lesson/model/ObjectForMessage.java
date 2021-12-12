package ru.otus.lesson.model;

import java.util.List;

public class ObjectForMessage {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public ObjectForMessage toClone() {
        var objectForMessage = new ObjectForMessage();
        objectForMessage.setData(List.copyOf(this.getData()));
        return objectForMessage;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (data != null) {
            for (String datum : data) {
                stringBuilder.append(datum);
            }
        }
        return "ObjectForMessage{" +
                "data=" + stringBuilder +
                '}';
    }
}
