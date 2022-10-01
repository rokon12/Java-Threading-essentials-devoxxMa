package com.bazlur.lab3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Playground {
    public static void main(String[] args) throws InterruptedException {

        Lock lock = new ReentrantLock();
        Thread thread = new Thread(() -> {
            int i = 0;
            System.out.println("Before entering to acquire the lock");
            try {
                lock.lockInterruptibly();
                while (true) {
                    System.out.println("In the block counting: " + i++);
                }
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }finally {
                lock.unlock();
            }
        });


        lock.lock(); // take the lock on same Lock object to make the lock in the thread "waiting" and then interruptible
        thread.start();
        thread.interrupt();


    }

    private final Lock lock = new ReentrantLock();

    public void method() throws InterruptedException {

        lock.lockInterruptibly();
        try {
            // ... method body
        } finally {
            lock.unlock();
        }
    }

}
