package com.bazlur;

import java.util.concurrent.Phaser;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairnessExperiment {
    private static final int PARTIES = 4;

    private static volatile boolean running = true;
    private static final Lock lock = new ReentrantLock(false);
    private static final Phaser phaser = new Phaser(PARTIES + 1);

    private static class Experiment extends Thread {
        private long count;

        @Override
        public void run() {
            phaser.arriveAndAwaitAdvance();
            while (running) {
                lock.lock();
                try {
                    count++;
                } finally {
                    lock.unlock();
                }
            }
            System.out.println("count = " + count);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        var experiments = new Experiment[PARTIES];
        for (int i = 0; i < experiments.length; i++) {
            experiments[i] = new Experiment();
            experiments[i].start();
        }

        phaser.arriveAndDeregister();
        Thread.sleep(1000);
        running = false;
    }
}
