package ma.devoxx;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);

        var t1 = new Thread(() -> {
            System.out.println("going to do some awesome work");
            sleep();
            System.out.println("Done!");
            latch.countDown();
        });

        var t2 = new Thread(() -> {
            System.out.println("going to do some awesome work");
            sleep();
            System.out.println("Done!");

            latch.countDown();
        });


        t1.start();
        t2.start();

        System.out.println("Let's wait until thread finish their work");

        latch.await();

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
