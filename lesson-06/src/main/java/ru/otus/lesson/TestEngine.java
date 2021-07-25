package ru.otus.lesson;

import ru.otus.lesson.annotations.After;
import ru.otus.lesson.annotations.Before;
import ru.otus.lesson.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class TestEngine {
    private static final String BEFORE_ANNOTATION_NAME = Before.class.getSimpleName();
    private static final String TEST_ANNOTATION_NAME = Test.class.getSimpleName();
    private static final String AFTER_ANNOTATION_NAME = After.class.getSimpleName();

    public static void testClass(String className) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class<?> clazz = Class.forName(className);

        test(clazz);
    }

    private static void test(Class<?> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor<?> constructor = clazz.getConstructor();
        Map<String, List<Method>> annotatedMethods = getMethodsWithTestableAnnotations(clazz);

        Set<Method> successMethods = new HashSet<>();
        Set<Method> failedMethods = new HashSet<>();

        List<Method> methodsWithTestAnnotation = annotatedMethods.get(TEST_ANNOTATION_NAME);
        for (Method methodWithTestAnnotation : methodsWithTestAnnotation) {
            Object newInstance = constructor.newInstance();

            for (Method methodWithBeforeAnnotation : annotatedMethods.get(BEFORE_ANNOTATION_NAME)) {
                methodWithBeforeAnnotation.invoke(newInstance);
            }

            try {
                methodWithTestAnnotation.invoke(newInstance);
            } catch (Exception e) {
                failedMethods.add(methodWithTestAnnotation);
            }

            for (Method methodWithAfterAnnotation : annotatedMethods.get(AFTER_ANNOTATION_NAME)) {
                methodWithAfterAnnotation.invoke(newInstance);
            }

            successMethods.add(methodWithTestAnnotation);
        }

        printStatistics(methodsWithTestAnnotation, successMethods, failedMethods);
    }

    private static void printStatistics(List<Method> methodTestList, Set<Method> successMethods, Set<Method> failedMethods) {
        int countOfTests = methodTestList.size();
        int countOfFailedTests = failedMethods.size();
        int countOfSuccessTests = successMethods.size() - countOfFailedTests;

        StringBuilder successTests = new StringBuilder();
        for (Method successMethod : successMethods) {
            successTests.append(successMethod.getName()).append(" ");
        }

        StringBuilder failedTests = new StringBuilder();
        for (Method failedMethod : failedMethods) {
            failedTests.append((failedMethod.getName()).concat(" "));
        }

        System.out.printf("Count of tests: %d\n" +
                        "Success: %d - %s\n" +
                        "Failed: %d - %s\n",
                countOfTests, countOfSuccessTests, successTests, countOfFailedTests, failedTests);
    }

    private static Map<String, List<Method>> getMethodsWithTestableAnnotations(Class<?> clazz) {
        Method[] methods = clazz.getMethods();

        return Map.of(BEFORE_ANNOTATION_NAME, getMethodsWithAnnotation(methods, Before.class),
                TEST_ANNOTATION_NAME, getMethodsWithAnnotation(methods, Test.class),
                AFTER_ANNOTATION_NAME, getMethodsWithAnnotation(methods, After.class));
    }

    private static List<Method> getMethodsWithAnnotation(Method[] methods, Class<? extends Annotation> clazz) {
        return Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(clazz))
                .collect(Collectors.toList());
    }
}