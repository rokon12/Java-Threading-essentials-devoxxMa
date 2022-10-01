package ca.bazlur;
//Ref: https://github.com/webtide/loom-trial/blob/main/src/main/java/org/webtide/loom/FakeDataBase.java

import org.openjdk.jmh.infra.Blackhole;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Stream;

public class FakeDatabase {

    private static final Random RANDOM = new SecureRandom();
    private static final Semaphore POOL = new Semaphore(100);

    private static final LongAdder result = new LongAdder();

    public static Object get(String key) {
        try {
            POOL.acquire();

            Thread.sleep(1 + RANDOM.nextInt(5));
            String result = Long.toHexString(key.hashCode() + RANDOM.nextLong()).repeat(5 + RANDOM.nextInt(5));
            Blackhole.consumeCPU(1000 + result.hashCode() % 1000);


            return result;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            POOL.release();
        }
    }

    public static long getResult() {
        return result.longValue();
    }

    public static void put(String key, Object value) throws InterruptedException {
        // pretend to marshal the update to the database
        long data = Stream.of(value.toString().toCharArray()).count();

        // pretend to get a connection from a JDBC connection pool
        POOL.acquire();

        // pretend to talk to a remote server
        Thread.sleep(2 + RANDOM.nextInt((int) (data % 3)));

        // pretend to care about the value
        result.add(key.hashCode());
        result.add(value.hashCode());
        result.add(data);

        // Put our thread back into the database.
        POOL.release();
    }

}
