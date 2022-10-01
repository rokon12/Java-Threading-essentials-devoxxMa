package ma.devoxx;

import java.util.concurrent.TimeUnit;

public class ThreadStoppingDemo {
    public static void main(String[] args) throws InterruptedException {

        var t = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()){
                //continue doing what you a
                System.out.println("Hello world!");
            }
        });
        t.start();

        TimeUnit.SECONDS.sleep(2);
        t.interrupt();
    }
}
