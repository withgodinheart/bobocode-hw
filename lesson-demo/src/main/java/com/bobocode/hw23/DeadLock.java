package com.bobocode.hw23;

import lombok.SneakyThrows;

public class DeadLock {

    public static final Object LOCK_1 = new Object();
    public static final Object LOCK_2 = new Object();

    @SneakyThrows
    public static void main(String[] args) {

        var t1 = new Thread(() -> {
            synchronized (LOCK_1) {
                System.out.println("Thread 1 -> LOCK_1");
                synchronized (LOCK_2) {
                    System.out.println("Thread 1 -> LOCK_2");
                }
            }
        });

        var t2 = new Thread(() -> {
            synchronized (LOCK_2) {
                System.out.println("Thread 2 -> LOCK_2");
                synchronized (LOCK_1) {
                    System.out.println("Thread 2 -> LOCK_1");
                }
            }
        });

        var t3 = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 1 -> " + t1.getState());
                System.out.println("Thread 2 -> " + t2.getState());
            }
        });

        t3.start();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
