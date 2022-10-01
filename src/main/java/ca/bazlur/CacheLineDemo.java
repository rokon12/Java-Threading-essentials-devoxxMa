package ca.bazlur;

public class CacheLineDemo {

    public static void main(String[] args) {
        var cache = new CacheLine();
        cache.run();
    }

}

class CacheLine {
    private final int ARR_SIZE = 2 * 1024 * 1024;
    private final int[] testData = new int[ARR_SIZE];

    public void run() {
        for (int i = 0; i < 10_000; i += 1) {
            touchEveryLine();
            touchEveryItem();
        }
        System.out.println("Line     Item");
        for (int i = 0; i < 100; i += 1) {
            long t0 = System.nanoTime();
            touchEveryLine();
            long t1 = System.nanoTime();
            touchEveryItem();
            long t2 = System.nanoTime();
            long elEveryLine = t1 - t0;
            long elEveryItem = t2 - t1;
            System.out.println(elEveryLine + " " + elEveryItem);
        }
    }

    private void touchEveryItem() {
        for (int i = 0; i < testData.length; i += 1)
            testData[i] += 1;
    }

    private void touchEveryLine() {
        for (int i = 0; i < testData.length; i += 8)
            testData[i] += 1;
    }
}
