package ma.devoxx;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class CyclicBareerExample {

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(2);

        var t1 = new Thread(() -> {
            System.out.println("going to do some awesome work");
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            System.out.println("all set go");

            sleep();
            System.out.println("Done!");
        });

        var t2 = new Thread(() -> {
            System.out.println("going to do some awesome work");
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            System.out.println("all set go");
            sleep();
            System.out.println("Done!");

        });


        t1.start();
        t2.start();

        System.out.println("Let's wait until everyone join ");

        System.out.println("Looks like they have done their work");
    }

    private static void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
