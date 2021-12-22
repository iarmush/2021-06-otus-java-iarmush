package ru.otus.messagesystem.message;

public enum MessageType {

    VOID_MESSAGE("Void"),
    SAVE_CLIENT("SaveClient"),
    EDIT_CLIENT("EditClient");

    private final String name;

    MessageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
