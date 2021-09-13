package ru.otus.lesson;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        Logging logging = new LoggingImpl();
        Logging loggingProxy = MyIoc.createMyClass(logging);
        loggingProxy.calculation(1);
        loggingProxy.calculation(2, 3);
        loggingProxy.calculation(4, 5, "6");

        Logging2 logging2 = new Logging2Impl();
        Logging2 loggingProxy2 = MyIoc.createMyClass(logging2);
        loggingProxy2.calculation2(7);
        loggingProxy2.calculation(8);
    }
}
