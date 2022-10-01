package ca.bazlur;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListExample {
    private static final List<Integer> INTEGERS = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        var write = new Thread(() -> {
            try {
                for (int i = 0; i < 50000; i++) {
                    INTEGERS.add(i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        var read = new Thread(() -> {
            try {
                for (int i = 0; i < 10000; i++) {
                    long total = 0;
                    for (Integer inList : INTEGERS) {
                        total += inList;
                    }
                    System.out.println(total);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        write.start();
        read.start();

        write.join();
        read.join();
    }

}
