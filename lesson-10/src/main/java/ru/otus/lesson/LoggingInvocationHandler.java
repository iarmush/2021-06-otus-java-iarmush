package ru.otus.lesson;

import ru.otus.lesson.annotation.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class LoggingInvocationHandler implements InvocationHandler {
    private final Class<? extends Annotation> LOG_ANNOTATION = Log.class;
    private final Set<Method> methodsSet;
    private final Set<String> methodsNameSet;
    private final Class<?> currentObject;

    public <T> LoggingInvocationHandler(T obj) throws ClassNotFoundException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        Method[] methods = classLoader.loadClass(obj.getClass().getCanonicalName()).getMethods();

        methodsSet = Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(LOG_ANNOTATION))
                .collect(Collectors.toSet());

        methodsNameSet = methodsSet.stream().map(Method::getName).collect(Collectors.toSet());

        currentObject = obj.getClass();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Constructor<?> constructor = currentObject.getConstructor();
        Object object = constructor.newInstance();

        methodsSet.forEach(m -> {
            for (Class<?> anInterface : m.getDeclaringClass().getInterfaces()) {
                if (method.getDeclaringClass().getName().equals(anInterface.getName())
                        && m.getName().equals(method.getName())
                        && Arrays.equals(m.getParameterTypes(), method.getParameterTypes())
                        && m.getReturnType().equals(method.getReturnType())) {
                    printLog(method.getName(), args);
                }
            }
        });

        return method.invoke(object, args);
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
