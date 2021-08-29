package ru.otus.lesson;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        Logging logging = new LoggingImpl();
        Logging loggingProxy = MyIoc.createMyClass(logging);
        loggingProxy.calculation(1);
        loggingProxy.calculation(2, 3);
        loggingProxy.calculation(4, 5, "6");
    }
}
