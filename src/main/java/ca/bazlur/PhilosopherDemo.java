package ca.bazlur;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class PhilosopherDemo {
    public static void main(String[] args) throws InterruptedException {
        var pool = Executors.newFixedThreadPool(5);

//        var forks = new Fork[5];
//        for (int i = 0; i < forks.length; i++) {
//            forks[i] = new Fork();
//        }

//        var philosophers = new Philosopher[5];
//        for (int i = 0; i < philosophers.length; i++) {
//            final var leftFork = forks[i];
//            final var rightFork = forks[(i + 1) % forks.length];
//
//            philosophers[i] = new Philosopher("Philosopher-" + (i + 1), leftFork, rightFork);
//            pool.execute(philosophers[i]);
//        }


//        for (int i = 0; i < philosophers.length; i++) {
//            final var leftFork = forks[i];
//            final var rightFork = forks[(i + 1) % forks.length];
//
//            if (i == philosophers.length - 1) {
//                philosophers[i] = new Philosopher("Philosopher-" + (i + 1), rightFork, leftFork);
//            } else {
//                philosophers[i] = new Philosopher("Philosopher-" + (i + 1), leftFork, rightFork);
//            }
//
//            pool.execute(philosophers[i]);
//        }


        var locks = new ReentrantLock[5];
        for (int i = 0; i < locks.length; i++) {
            locks[i] = new ReentrantLock();
        }

        var philosophers = new ModernPhilosopher[5];
        for (int i = 0; i < philosophers.length; i++) {
            final var leftFork = locks[i];
            final var rightFork = locks[(i + 1) % locks.length];
            philosophers[i] = new ModernPhilosopher("Philosopher-" + (i + 1), leftFork, rightFork);
            pool.execute(philosophers[i]);
        }
    }
}
