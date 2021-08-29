package ru.otus.lesson;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class MyIoc {
    public static Logging createMyClass(Object obj) throws ClassNotFoundException {
        if (obj instanceof Logging) {
            InvocationHandler invocationHandler = new LoggingInvocationHandler();
            return (ru.otus.lesson.Logging) Proxy.newProxyInstance(MyIoc.class.getClassLoader(),
                    new Class<?>[]{ru.otus.lesson.Logging.class}, invocationHandler);

        } else throw new ClassNotFoundException(obj.getClass().getName());
    }
}