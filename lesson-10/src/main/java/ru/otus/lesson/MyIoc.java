package ru.otus.lesson;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class MyIoc {
    public static ru.otus.lesson.Logging createMyClass() throws ClassNotFoundException {
        InvocationHandler invocationHandler = new MyInvocationHandler();
        return (ru.otus.lesson.Logging) Proxy.newProxyInstance(MyIoc.class.getClassLoader(),
                new Class<?>[]{ru.otus.lesson.Logging.class}, invocationHandler);
    }
}