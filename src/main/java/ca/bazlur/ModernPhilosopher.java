package ca.bazlur;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.locks.Lock;

public class ModernPhilosopher implements Runnable {
    private final String name;
    private final Lock left;
    private final Lock right;

    public ModernPhilosopher(String name, Lock left, Lock right) {
        this.name = name;
        this.left = left;
        this.right = right;
    }

    private void think() {
        log("thinking");
    }

    private void eat() {
        // let's assume, eating takes some time.
        try {
            log("eating");
            final var eatingTime = getRandomEatingTime();
            Thread.sleep(eatingTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    private int getRandomEatingTime() {
        return new Random().nextInt(100) + 50;
    }

    private void keepThinkingAndEating() {
        think();
        if (left.tryLock()) {
            try {
                log("grabbed left fork");
                if (right.tryLock()) {
                    try {
                        log("grabbed right fork");
                        eat();

                    } finally {
                        log("put down right fork");
                        right.unlock();
                    }
                }
            } finally {
                left.unlock();
            }
        }

    }

    @Override
    public void run() {
        while (true) {
            keepThinkingAndEating();
        }
    }

    private void log(String message) {
        System.out.printf("%12s Thread: %s %s %s%n",
                DateTimeFormatter.ISO_LOCAL_TIME.format(LocalDateTime.now()), Thread.currentThread(), name, message);
    }
}
