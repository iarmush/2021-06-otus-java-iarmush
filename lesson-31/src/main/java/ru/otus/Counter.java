package ru.otus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Counter {

    private static final Logger logger = LoggerFactory.getLogger(Counter.class);

    private static final int LIMIT = 10;
    private static final String TH_1 = "th1";
    private static final String TH_2 = "th2";
    private static String first = TH_1;

    Map<String, AtomicInteger> threadMap = new HashMap<>() {{
        put(TH_1, new AtomicInteger(1));
        put(TH_2, new AtomicInteger(1));
    }};

    public static void main(String[] args) {
        var counter = new Counter();
        new Thread(() -> counter.count(TH_1)).start();
        new Thread(() -> counter.count(TH_2)).start();
    }

    private synchronized void count(String threadName) {
        boolean goUp = true;
        while (!Thread.currentThread().isInterrupted() && threadMap.get(threadName).get() != 0) {
            if (threadMap.get(threadName).get() == LIMIT) {
                goUp = false;
            }

            try {
                while (!threadName.equals(first)) {
                    this.wait();
                }
                logger.info(
                    Thread.currentThread().getName() + "-" + threadName + ", counter value: " + threadMap.get(threadName)
                        .getAndAdd(goUp ? 1 : -1));
                first = getAnotherThreadName(threadName);
                notify();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private String getAnotherThreadName(String currentThreadName) {
        return threadMap.keySet().stream().filter(name -> !name.equals(currentThreadName)).findFirst().orElseThrow();
    }
}
