package ca.bazlur;

public class DeadLockDemoFix {
    public static void main(String[] args) {

        final Object lockA = new Object();
        final Object lockB = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (lockA) {
                sleep();
                synchronized (lockB) {
                    compute();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (lockA) {
                synchronized (lockB) {
                    compute();
                }
            }
        });


        t1.start();
        t2.start();
    }

    private static void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
    }

    private static void compute() {
        System.out.println("Computing");
    }
}
