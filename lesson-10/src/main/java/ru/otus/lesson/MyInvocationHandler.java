package ru.otus.lesson;

import ru.otus.lesson.annotation.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class MyInvocationHandler implements InvocationHandler {
    private final Class<? extends Annotation> LOG_ANNOTATION = Log.class;
    private final ru.otus.lesson.Logging myClass = new ru.otus.lesson.LoggingImpl();
    private final Set<String> methodsNamesSet;

    MyInvocationHandler() throws ClassNotFoundException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        Method[] methods = classLoader.loadClass(LoggingImpl.class.getCanonicalName()).getMethods();

        methodsNamesSet = Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(LOG_ANNOTATION))
                .map(Method::getName)
                .collect(Collectors.toSet());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if (methodsNamesSet.contains(methodName)) {
            printLog(methodName, args);
        }
        return method.invoke(myClass, args);
    }

    private void printLog(String methodName, Object[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("executed method: ")
                .append(methodName)
                .append(", ")
                .append("param: ");
        for (int i = 0; i < args.length; i++) {
            stringBuilder.append(args[i]);
            if (i != args.length - 1) {
                stringBuilder.append(", ");
            }
        }
        System.out.println(stringBuilder);
    }
}
