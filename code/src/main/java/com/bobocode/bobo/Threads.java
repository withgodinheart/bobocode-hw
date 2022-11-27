package com.bobocode.bobo;

import lombok.SneakyThrows;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.FutureTask;

public class Threads {

    @SneakyThrows
    public static void main(String[] args) {

        Callable<String> callable = () -> {
            System.out.println("From Future callable: " + Thread.currentThread().getName());
            return "From Future";
        };
        Runnable runnable = () -> System.out.println("From Thread runnable: " + Thread.currentThread().getName());
        var callable1 = new Callable<>() {
            @Override
            public Object call() throws Exception {
                return null;
            }
        };

        new Thread(runnable).start(); // accept runnable, not callable
        new FutureTask<>(callable).run();// accept callable, not runnable

        CompletableFuture.runAsync(() -> System.out.println("From Completable future runAsync: " + Thread.currentThread().getName())).get();
        var res = CompletableFuture.supplyAsync(() -> {
            System.out.println("From Completable future supplyAsync: " + Thread.currentThread().getName());
            return "From Completable future";
        }).get();
    }
}
