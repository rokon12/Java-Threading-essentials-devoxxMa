package ma.devoxx;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BufferWithBlockingQueue {

    private final int SIZE = 10;
    private BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(SIZE);

    public void produce(Integer item) {
        System.out.println("producing "+ item + " from: "+ Thread.currentThread());
        queue.add(item);
    }

    public  Integer consume() {
        final var poll = queue.poll();

        System.out.println("consuming " + poll+ " from: "+ Thread.currentThread());
        return poll;
    }

    public static void main(String[] args) {

        var buffer = new BufferWithBlockingQueue();

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
