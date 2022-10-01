package ca.bazlur;

public class BufferDemo {
    public static void main(String[] args) throws InterruptedException {
        var buffer = new BufferWithBlockingQueue();

        var t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                buffer.addItem(i);
            }
        });

        var t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                buffer.getItem();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
