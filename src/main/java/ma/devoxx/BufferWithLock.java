package ma.devoxx;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferWithLock {
    private final static int SIZE = 10;
    private final Queue<Integer> queue = new LinkedList<>();
    private final Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();


    public void addItem(int item) {
        try {
            lock.lock();
            while (isFull()) {
                try {
                    notFull.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            queue.add(item);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    private boolean isFull() {
        return queue.size() == SIZE;
    }

    public Integer getItem() {
        try {
            lock.lock();
            while (queue.isEmpty()) {
                try {
                    notEmpty.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            final var poll = queue.poll();
            notFull.signal();
            return poll;
        } finally {
            lock.unlock();
        }
    }
}
