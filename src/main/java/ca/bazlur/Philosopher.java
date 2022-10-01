package ca.bazlur;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Philosopher implements Runnable {
    private final String name;
    private final Fork left;
    private final Fork right;

    public Philosopher(String name, Fork left, Fork right) {
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
        synchronized (left) {
            log("grabbed left fork");
            synchronized (right) {
                log("grabbed right fork");
                eat();
                log("put down right fork");
            }
            log("put down left fork");
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

class Fork {
}
