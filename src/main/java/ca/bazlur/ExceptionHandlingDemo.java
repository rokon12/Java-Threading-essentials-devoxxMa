package ca.bazlur;

import java.util.concurrent.TimeUnit;

public class ExceptionHandlingDemo {
    public static void main(String[] args) throws InterruptedException {
        var thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                System.err.println("Thread Interrupted deu to " + e.getMessage());
            }
            throw new RuntimeException("Goodbye cruel world!");
        });

        thread.setUncaughtExceptionHandler((t, e) -> {
            e.printStackTrace();
        });

        thread.start();
        thread.join();

    }
}
