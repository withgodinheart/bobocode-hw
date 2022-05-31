package com.bobocode.hw9;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class Threads {

    public static void main(String[] args) {
        Runnable task = () -> System.out.println("Hello from " + Thread.currentThread().getName());
        task.run();
        startThread(task);
        useExecutorService(task);
        streamParallel(task);
    }

    private static void streamParallel(final Runnable runnable) {
        Stream.of(runnable).forEach(Runnable::run);
    }

    private static void startThread(final Runnable runnable) {
        new Thread(runnable).start();
    }

    private static void useExecutorService(final Runnable runnable) {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(runnable);
        executorService.shutdown();
    }
}
