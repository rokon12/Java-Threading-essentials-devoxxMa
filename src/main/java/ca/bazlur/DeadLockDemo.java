package ca.bazlur;

public class DeadLockDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("started");
        final Object lockA = new Object();
        final Object lockB = new Object();

        Thread t1 =
                new Thread(
                        () -> {
                            synchronized (lockA) {
                                sleep();
                                synchronized (lockB) {
                                    compute();
                                }
                            }
                        });

        Thread t2 =
                new Thread(
                        () -> {
                            synchronized (lockB) {
                                synchronized (lockA) {
                                    compute();
                                }
                            }
                        });
        t1.start();
        t2.start();

        t1.join();
        t2.join();
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
