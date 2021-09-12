package ru.otus.lesson;

import java.lang.reflect.Proxy;

public class MyIoc {
    public static <T> T createMyClass(T obj) throws ClassNotFoundException {
        return (T) Proxy.newProxyInstance(MyIoc.class.getClassLoader(),
                new Class<?>[]{obj.getClass().getInterfaces()[0]}, new LoggingInvocationHandler(obj.getClass()));
    }
}