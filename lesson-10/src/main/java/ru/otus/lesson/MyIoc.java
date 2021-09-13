package ru.otus.lesson;

import java.lang.reflect.Proxy;

public class MyIoc {
    public static <T> T createMyClass(T obj) throws ClassNotFoundException {
        Class<?> requiredInterface = null;
        Class<?>[] interfaces = obj.getClass().getInterfaces();
        for (Class<?> anInterface : interfaces) {
            if (anInterface.isInstance(obj)) {
                requiredInterface = anInterface;
            }
        }

        if (requiredInterface == null) {
            throw new RuntimeException();
        } else return (T) Proxy.newProxyInstance(MyIoc.class.getClassLoader(),
                new Class<?>[]{requiredInterface}, new LoggingInvocationHandler(obj));
    }
}