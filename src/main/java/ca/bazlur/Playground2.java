package ca.bazlur;

import java.lang.Thread.UncaughtExceptionHandler;

public class Playground2 {
  public static void main(String[] args) throws InterruptedException {
    var t = new Thread(() -> {
      for(int i = 0; i < 1005; i++) {
        System.out.println(i);
      }
    });

    t.setDaemon(true);
    t.start();


    t.join();

  }
}
