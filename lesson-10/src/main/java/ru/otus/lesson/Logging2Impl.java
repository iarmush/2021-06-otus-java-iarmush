package ru.otus.lesson;

import ru.otus.lesson.annotation.Log;

public class Logging2Impl implements Logging2 {

    @Log
    @Override
    public void calculation(int a) {
        System.out.println(a + "!");
    }

    @Log
    @Override
    public void calculation2(int a) {
        System.out.println(a);
    }
}
