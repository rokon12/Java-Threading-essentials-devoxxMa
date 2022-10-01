package ca.bazlur;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Im running on: " + Thread.currentThread());

    var thread =
        new Thread(
                () -> System.out.println("Im running on: " + Thread.currentThread())
        );

        thread.setName("SouJava Thread");
        thread.start();

        thread.join();
    }
}
