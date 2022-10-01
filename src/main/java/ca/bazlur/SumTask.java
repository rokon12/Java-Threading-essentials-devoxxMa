package ca.bazlur;

import java.util.concurrent.RecursiveTask;

public class SumTask extends RecursiveTask<Integer> {
    private final int[] array;
    private final int low;
    private final int high;

    private static final int THRESH_HOLD = 10_000;

    public SumTask(int[] array, int low, int high) {
        this.array = array;
        this.low = low;
        this.high = high;
    }

    @Override
    protected Integer compute() {
        if (high - low > THRESH_HOLD) {
            int mid = ((high - low) + low) / 2;

            SumTask left = new SumTask(array, low, mid);
            SumTask right = new SumTask(array, mid, high);

            var resultLeft = left.join();
            var resultOfRight = right.compute();

            return resultOfRight + resultLeft;
        } else {
            return computeDirectly(array, low, high);
        }
    }

    private Integer computeDirectly(int[] array, int low, int high) {
        int sum = 0;
        for (int i = low; i < high; i++) {
            sum += array[i];
        }
        return sum;
    }
}
