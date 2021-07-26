package ru.otus.lesson.annotation.processing;

import ru.otus.lesson.annotation.After;
import ru.otus.lesson.annotation.Before;
import ru.otus.lesson.annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class TestEngine {
    public static final String ERROR_DURING_TEST_EXECUTION = "Error during test execution";

    public static void testClass(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            test(clazz);
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            throw new RuntimeException(ERROR_DURING_TEST_EXECUTION);
        }
    }

    private static void test(Class<?> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor<?> constructor = clazz.getConstructor();
        Map<Class<? extends Annotation>, List<Method>> annotatedMethods = getMethodsWithTestableAnnotations(clazz);

        Set<Method> successMethods = new HashSet<>();
        Set<Method> failedMethods = new HashSet<>();

        List<Method> methodsWithTestAnnotation = annotatedMethods.get(Test.class);
        List<Method> methodsWithBeforeAnnotation = annotatedMethods.get(Before.class);
        List<Method> methodsWithAfterAnnotation = annotatedMethods.get(After.class);

        for (Method methodWithTestAnnotation : methodsWithTestAnnotation) {
            Object newInstance = constructor.newInstance();

            try {
                for (Method methodWithBeforeAnnotation : methodsWithBeforeAnnotation) {
                    methodWithBeforeAnnotation.invoke(newInstance);
                }
            } catch (Exception e) {
                for (Method methodWithAfterAnnotation : methodsWithAfterAnnotation) {
                    methodWithAfterAnnotation.invoke(newInstance);
                }
                return;
            }

            try {
                methodWithTestAnnotation.invoke(newInstance);
                successMethods.add(methodWithTestAnnotation);
            } catch (Exception e) {
                failedMethods.add(methodWithTestAnnotation);
            } finally {
                for (Method methodWithAfterAnnotation : methodsWithAfterAnnotation) {
                    methodWithAfterAnnotation.invoke(newInstance);
                }
            }
        }

        printStatistics(methodsWithTestAnnotation, successMethods, failedMethods);
    }

    private static void printStatistics(List<Method> methodTestList, Set<Method> successMethods, Set<Method> failedMethods) {
        int countOfTests = methodTestList.size();
        int countOfFailedTests = failedMethods.size();
        int countOfSuccessTests = successMethods.size();

        StringBuilder successTests = new StringBuilder();
        for (Method successMethod : successMethods) {
            successTests.append(successMethod.getAnnotation(Test.class).value()).append(" ");
        }

        StringBuilder failedTests = new StringBuilder();
        for (Method failedMethod : failedMethods) {
            failedTests.append(failedMethod.getAnnotation(Test.class).value()).append(" ");
        }

        System.out.printf("----------------------\n" +
                        "Total: %d\n" +
                        "Success: %d - %s\n" +
                        "Failed: %d - %s\n",
                countOfTests, countOfSuccessTests, successTests, countOfFailedTests, failedTests);
    }

    private static Map<Class<? extends Annotation>, List<Method>> getMethodsWithTestableAnnotations(Class<?> clazz) {
        Method[] methods = clazz.getMethods();

        return Map.of(Before.class, getMethodsWithAnnotation(methods, Before.class),
                Test.class, getMethodsWithAnnotation(methods, Test.class),
                After.class, getMethodsWithAnnotation(methods, After.class));
    }

    private static List<Method> getMethodsWithAnnotation(Method[] methods, Class<? extends Annotation> clazz) {
        return Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(clazz))
                .collect(Collectors.toList());
    }
}