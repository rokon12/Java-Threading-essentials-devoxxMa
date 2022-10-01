package ma.devoxx;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Counter {
    private long count; // = 5

    public synchronized void increment() {
        count = count + 1;
        // read,
        // add +1,
        // replace the value
    }


    public static void main(String[] args) throws InterruptedException {
        //var counter = new Counter();

        var counter = new AtomicInteger();

        var t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.incrementAndGet();
            }
        });
        var t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.incrementAndGet();
            }
        });

        t1.start();
        t2.start();


        t1.join();
        t2.join();

        System.out.println(counter.get());

    }

    // t1   :  t2
    // 5       5
    // 6       6
    // 6
}
