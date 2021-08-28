package ru.calculator;


/*
-Xms256m
-Xmx256m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/heapdump.hprof
-XX:+UseG1GC
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
*/


// Запуск из idea до оптимизации:
// 2048m - spend msec:12629, sec:12
// 1024m - spend msec:12728, sec:12
// 512m - spend msec:12258, sec:12
// 256m - spend msec:12799, sec:12
// 128m - spend msec:14015, sec:14

// Запуск из idea после оптимизации:
// 2048m - spend msec:4421, sec:4
// 1024m - spend msec:3497, sec:3
// 512m - spend msec:3193, sec:3
// 256m - spend msec:3274, sec:3
// 128m - spend msec:4071, sec:4


// Запуск jar файла из консоли до оптимизации:
// 2048m - spend msec:11440, sec:11
// 1024m - spend msec:11273, sec:11
// 512m - spend msec:12262, sec:12
// 256m - Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
// 128m - Exception in thread "main" java.lang.OutOfMemoryError: Java heap space

// Запуск jar файла из консоли после оптимизации:
// 2048m - spend msec:3197, sec:3
// 1024m - spend msec:2427, sec:2
// 512m - spend msec:3801, sec:3
// 256m - spend msec:6477, sec:6
// 128m - Exception in thread "main" java.lang.OutOfMemoryError: Java heap space

import java.time.LocalDateTime;

public class CalcDemo {

    public static final LocalDateTime NOW = LocalDateTime.now();

    public static void main(String[] args) {
        long counter = 100_000_000;
        var summator = new Summator();
        long startTime = System.currentTimeMillis();

        for (var idx = 0; idx < counter; idx++) {
            Data data = new Data(idx);
            summator.calc(data);

            if (idx % 10_000_000 == 0) {
                System.out.println(NOW + " current idx:" + idx);
            }
        }

        long delta = System.currentTimeMillis() - startTime;
        System.out.println(summator.getPrevValue());
        System.out.println(summator.getPrevPrevValue());
        System.out.println(summator.getSumLastThreeValues());
        System.out.println(summator.getSomeValue());
        System.out.println(summator.getSum());
        System.out.println("spend msec:" + delta + ", sec:" + (delta / 1000));
    }
}
