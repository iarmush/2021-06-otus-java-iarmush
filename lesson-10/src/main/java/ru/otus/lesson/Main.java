package ru.otus.lesson;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        Logging testLogging = MyIoc.createMyClass();
        testLogging.calculation(1);
        testLogging.calculation(2, 3);
        testLogging.calculation(4, 5, "6");
    }
}
