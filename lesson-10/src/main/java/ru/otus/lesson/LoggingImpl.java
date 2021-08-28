package ru.otus.lesson;

import ru.otus.lesson.annotation.Log;

public class LoggingImpl implements ru.otus.lesson.Logging {

    @Log
    @Override
    public void calculation(int a) {
        System.out.println(a);
    }

    @Log
    @Override
    public void calculation(int a, int b) {
        System.out.println(a + b);
    }

    @Log
    @Override
    public void calculation(int a, int b, String c) {
        System.out.println(a + b + c);
    }
}
