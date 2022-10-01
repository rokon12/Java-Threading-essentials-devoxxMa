package ca.bazlur;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
    private AtomicInteger counter = new AtomicInteger();

    public int incrementAndGet() {
        return counter.incrementAndGet();
    }

    public int getCount() {
        return counter.get();
    }
}
