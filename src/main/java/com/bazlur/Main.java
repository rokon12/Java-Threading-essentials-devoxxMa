package com.bazlur;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        System.out.println("Hello world!");
//        System.out.println("I'm running from: " + Thread.currentThread());
//
//
//        var t1 = new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        System.out.println("I'm running from: " + Thread.currentThread());
//                    }
//                }
//        );
//        t1.setName("Duke#1");
//
//        t1.start();
//
//        var t2 = new Thread(new MyTask());
//        t2.start();
//
//


        var counter = new IntCounter();

        var t1 = new Thread(() -> {
            for (int i = 0; i < 1_000; i++) {
                counter.incrementAndGet();
            }
        });

        var t2 = new Thread(() -> {
            for (int i = 0; i < 1_000; i++) {
                counter.incrementAndGet();
            }
        });

        var t3 = new Thread(() -> {
            for (int i = 0; i < 1_000; i++) {
                counter.incrementAndGet();
            }
        });

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        int count = counter.getCount();
        System.out.println("count = " + count);
    }
}

//$ java Main.java

//output:
//Hello world!
//I'm running from: Thread[main,5,main]
