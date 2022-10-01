package com.bazlur;

public class IntCounter {
  private volatile int count;

  private final Object lock = new Object();

  public int incrementAndGet() {
    count = count + 1;
    return count;
  }

  public int getCount() {
    return count;
  }
}
