package ca.bazlur;

import java.util.LinkedList;
import java.util.Queue;

public class Buffer {
    private final static int SIZE = 10;
    private final Queue<Integer> queue = new LinkedList<>();

    public synchronized void addItem(int item) {
        while (queue.size() == SIZE) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        queue.add(item);

        notifyAll();
    }

    public synchronized Integer getItem() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Integer value = queue.poll();
        notifyAll();

        return value;
    }
}
