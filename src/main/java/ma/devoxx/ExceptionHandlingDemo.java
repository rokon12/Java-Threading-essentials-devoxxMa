package ma.devoxx;

public class ExceptionHandlingDemo {
    public static void main(String[] args) throws InterruptedException {
        var t = new Thread(() -> {
            throw new RuntimeException("Hello world");
        });

        t.setUncaughtExceptionHandler((t1, e) -> {
            e.printStackTrace();
        });

        t.start();
        t.join();
    }
}
