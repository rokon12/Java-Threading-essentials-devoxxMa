package ca.bazlur;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BufferWithBlockingQueue {
    private final static int SIZE = 10;
    private final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(SIZE);

    public  void addItem(int item) {
       queue.add(item);
    }

    public synchronized Integer getItem() {
        return queue.poll();
    }

}
