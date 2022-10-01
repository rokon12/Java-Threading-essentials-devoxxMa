package ma.devoxx;

import java.util.LinkedList;
import java.util.Queue;

public class Buffer {

    private final int SIZE = 10;
    private Queue<Integer> queue = new LinkedList<>();

    private Object lock = new Object();

    public synchronized void produce(Integer item) {

        while (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("producing " + item + " from: " + Thread.currentThread());
        queue.add(item);

        notifyAll();

    }

    public synchronized Integer consume() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        final var poll = queue.poll();

        notifyAll();

        System.out.println("consuming " + poll + " from: " + Thread.currentThread());
        return poll;
    }

    private boolean isFull() {
        return queue.size() == SIZE;
    }

    public static void main(String[] args) {

        var buffer = new Buffer();

        var t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                buffer.produce(i);
            }
        });
        t1.setName("Producer");

        var t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                final var value = buffer.consume();
            }
        });
        t2.setName("Consumer");

        t1.start();
        t2.start();
    }

}
