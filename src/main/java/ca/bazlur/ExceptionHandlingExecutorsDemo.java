package ca.bazlur;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ExceptionHandlingExecutorsDemo {
    public static void main(String[] args) {

        var pool = Executors.newFixedThreadPool(10, new MyThreadFactory());

        pool.execute(() -> {
            System.out.println("Just chilling!! ");
            throw new RuntimeException("Ah!! ");
        });

        pool.shutdown();
    }

    static class MyThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            final var thread = new Thread(r);
            thread.setUncaughtExceptionHandler(new AppExceptionHandler());
            return thread;
        }
    }

    static class AppExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println("Uncaught Exception occurred on thread: " + t.getName());
            System.out.println("Exception message: " + e.getMessage());
        }
    }
}
