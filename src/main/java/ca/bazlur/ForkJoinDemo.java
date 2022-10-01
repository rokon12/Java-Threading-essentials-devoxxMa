package ca.bazlur;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class ForkJoinDemo {
    public static void main(String[] args) {

        int[] ints = new int[10_000_000];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = i + 2;
        }

        IntStream.of(ints).sum();

        var forkJoinPool = ForkJoinPool.commonPool();
        var submit = forkJoinPool.invoke(new SumTask(ints, 0, ints.length));
        System.out.println(submit);

    }
}
