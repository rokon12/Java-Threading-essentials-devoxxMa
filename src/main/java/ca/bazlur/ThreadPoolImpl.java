package ca.bazlur;

import java.util.concurrent.*;

public class ThreadPoolImpl implements ThreadPool {
    private static final int MAX_ALLOWED_THREAD = 1024;

    private final BlockingQueue<Runnable> runQueue = new LinkedBlockingDeque<>();

    private volatile boolean running = true;

    private final ThreadGroup threadGroup = new ThreadGroup("MyThreadGroup");

    public ThreadPoolImpl(int threadCount) {
        if (threadCount < 0 || threadCount > MAX_ALLOWED_THREAD) {
            throw new IllegalArgumentException("Max allowed thread count is " + MAX_ALLOWED_THREAD);
        }

        for (int i = 0; i < threadCount; i++) {
            Worker worker = new Worker(threadGroup, "Worker " + i);
            worker.start();
        }
    }

    @Override
    public void submit(Runnable job) {

        runQueue.add(job);
    }

    @Override
    public <T> Future<T> submit(Callable<T> jobs) {
        FutureTask<T> task = new FutureTask<>(jobs);
        runQueue.add(task);

        return task;
    }

    private class FutureTask<T> implements Runnable, Future<T> {
        private Callable<T> callable;

        private volatile T result;
        private volatile boolean done = false;
        private volatile boolean cancel = false;

        public FutureTask(Callable<T> callable) {
            this.callable = callable;
        }

        @Override
        public void run() {
            try {
                final T result = this.callable.call();
                this.result = result;
                this.done = true;
            } catch (Exception e) {
                this.cancel = true;
            }
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            if (isDone()) return false;
            Thread.currentThread().interrupt();
            return true;
        }

        @Override
        public boolean isCancelled() {
            return this.cancel;
        }

        @Override
        public boolean isDone() {

            return this.done;
        }

        @Override
        public T get() throws InterruptedException, ExecutionException {
            while (!isDone()) {
            }

            return this.result;
        }

        @Override
        public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            //
            return null;
        }
    }

    @Override
    public void shutdown() {
        this.running = false;
        this.threadGroup.interrupt();
    }

    private Runnable take() throws InterruptedException {
        return runQueue.take();
    }

    private class Worker extends Thread {

        public Worker(ThreadGroup threadGroup, String name) {
            super(threadGroup, name);
        }

        @Override
        public void run() {
            while (running && !interrupted()) {
                try {
                    final Runnable job = take();
                    job.run();
                } catch (InterruptedException e) {
                    this.interrupt();
                }
            }
        }
    }
}
