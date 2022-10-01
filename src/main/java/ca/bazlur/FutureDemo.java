package ca.bazlur;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final var pool = Executors.newFixedThreadPool(10);



        final var futureDemo = new FutureDemo();
//        final var user = futureDemo.findUser(); // 100 mili
//        final var order = futureDemo.getOrder(); // 200 mill

        final Future<?> future1 = pool.submit(() -> futureDemo.findUser());
        final var future = pool.submit(() -> futureDemo.getOrder());


        // asdfllafl
        //asdfjasldf
        //

        final Object o = future1.get();
        final var integer = future.get();

        final var submit = pool.submit(() -> {
            System.out.println("I will throw RuntimeException now.");
            throw new RuntimeException("Planned exception after execute()");
        });

        //final var o1 = submit.get();

        pool.shutdown();
    }

    public String findUser() {
        //call third party service

        return "bazlur";
    }

    public int getOrder() {
        //call third party service
        return 134;
    }
}
