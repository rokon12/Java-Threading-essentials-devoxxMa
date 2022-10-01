package ca.bazlur;

public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("A whole new world!");
        System.out.println("Im running on: " + Thread.currentThread());
    }
}
