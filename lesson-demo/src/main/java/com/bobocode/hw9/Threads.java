package com.bobocode.hw9;

import lombok.SneakyThrows;

import java.util.List;
import java.util.concurrent.CompletableFuture;
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
        completableFuture(task);
//        streamParallel2(task);
    }

    @SneakyThrows
    private static void completableFuture(final Runnable runnable) {
        CompletableFuture.runAsync(runnable);
    }

    private static void streamParallel(final Runnable runnable) {
        Stream.of(runnable).forEach(Runnable::run);
    }

    private static void streamParallel2(final Runnable runnable) {
        List.of(runnable, runnable).parallelStream().forEach(Runnable::run);
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
