package com.bobocode.hw9;

import lombok.SneakyThrows;

public class DeadLock {

    private static String lock1 = "lock1";
    private static String lock2 = "lock2";

    @SneakyThrows
    public static void main(String[] args) {

        var t1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 1 -> lock 1");
                synchronized (lock2) {
                    System.out.println("Thread 1 -> lock 2");
                }
            }
        });

        var t2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("Thread 2 -> lock 2");
                synchronized (lock1) {
                    System.out.println("Thread 2 -> lock 1");
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
                System.out.println(t1.getState());
                System.out.println(t2.getState());
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
    }
}
