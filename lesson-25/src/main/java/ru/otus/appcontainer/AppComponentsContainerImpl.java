package ru.otus.appcontainer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        Object config = invokeConfigConstructor(configClass);
        Reflections reflections = new Reflections(configClass, new MethodAnnotationsScanner());
        reflections.getMethodsAnnotatedWith(AppComponent.class).stream()
            .sorted(Comparator.comparingInt(Method::getParameterCount))
            .forEach(method -> registerAppComponent(config, method));
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return appComponents.stream()
            .filter(obj -> componentClass.isAssignableFrom(obj.getClass()))
            .findFirst()
            .map(obj -> (C) obj)
            .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        if (!appComponentsByName.containsKey(componentName)) {
            throw new IllegalArgumentException();
        }
        return (C) appComponentsByName.get(componentName);
    }

    private Object invokeConfigConstructor(Class<?> configClass) {
        try {
            return configClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void registerAppComponent(Object config, Method method) {
        try {
            AppComponent appComponent = method.getDeclaredAnnotation(AppComponent.class);
            Class<?>[] parameterTypes = method.getParameterTypes();
            Object[] args = new Object[method.getParameterCount()];
            for (int i = 0; i < parameterTypes.length; ++i) {
                args[i] = getAppComponent(parameterTypes[i]);
            }
            Object result = method.invoke(config, args);
            appComponentsByName.put(appComponent.name(), result);
            appComponents.add(result);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }
}
