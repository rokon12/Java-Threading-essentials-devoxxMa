package ma.devoxx;

import java.util.ArrayList;
import java.util.List;

public class Playground {


    private static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {

//        var t = new Thread(() -> {
//            for (int i = 0; i < 10; i++) {
//                System.out.println("Hello JUGBD! Im running inside:  "+ Thread.currentThread().getName());
//            }
//        });
//
//        t.setName("JUGBD");
//        t.start();

//        var myThread = new MySuperCoolThread();
//        myThread.start();

//        var myrunable = new MySuperCoolRunnable();
//        var t2 = new Thread(myrunable);
//        t2.setName("MyRunnable");
//
//        t2.start();


//        var t3 = new Thread(() -> {
//            while (!Thread.interrupted()) {
//                System.out.println("Hello world");
//                System.out.println("hello world 2");
//            }
//            System.out.println("My work is done, I stop now");
//        });
//
//        t3.start();
//
//        Thread.sleep(1000);
//        t3.interrupt();

        var t4 = new Thread(() -> {
            System.out.println("Im inside "+ Thread.currentThread());

            //try {
            if (true)
                throw new RuntimeException("Goodbye");
//            }catch (Throwable e){
//                System.out.println("caught exceptions");
//            }

            System.out.println("Hello");

        });


        t4.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                e.printStackTrace();
            }
        });




        t4.start();

        System.out.println("Hello JUGBD! Im running inside:  " + Thread.currentThread().getName());

    }


    static class MySuperCoolRunnable implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("Hello JUGBD! Im running inside:  " + Thread.currentThread().getName());
            }
        }
    }


    static class MySuperCoolThread extends Thread {

        public MySuperCoolThread() {
            super("MySuperCoolThread");
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("Hello JUGBD! Im running inside:  " + Thread.currentThread().getName());
            }
        }
    }
}
