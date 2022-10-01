package ca.bazlur;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        var pool = new ThreadPoolImpl(20);
        pool.submit(() -> print());
        pool.submit(() -> print());
        pool.submit(() -> print());
        pool.submit(() -> print());
        pool.submit(() -> print());

        final Future<Integer> submit = pool.submit(() -> expensiveOp());
        final Integer integer = submit.get();
        System.out.println(integer);

        TimeUnit.MINUTES.sleep(1);

        pool.shutdown();
    }

    private static int expensiveOp() {
        return 5;
    }

    private static void print() {
        System.out.println(Thread.currentThread().getName() + ": Hello, world!");
    }
}
