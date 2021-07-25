package ru.otus.lesson;

import ru.otus.lesson.annotations.After;
import ru.otus.lesson.annotations.Before;
import ru.otus.lesson.annotations.Test;

public class ExampleTest {

    @Before
    public void annotationBefore() {
        System.out.println("Before method executed");
    }

    @After
    public void annotationAfter() {
        System.out.println("After method executed");
    }

    @Test("Test 1")
    public void methodTest1() {
        System.out.println("methodTest1() executed");
    }

    @Test("Test 2")
    public void methodTest2() {
        System.out.println("methodTest2() executed");
    }

    @Test("Test 3")
    public void methodTest3() {
        System.out.println("methodTest3() executed");
        throw new RuntimeException();
    }

    @Test("Test 4")
    public void methodTest4() {
        System.out.println("methodTest4() executed");
    }
}
