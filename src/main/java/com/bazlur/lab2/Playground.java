package com.bazlur.lab2;


import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

public class Playground {
  static   ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        var start = Instant.now();

//        calculateCreditForPerson3(threadPool);
        //calculateCreditForPerson2();
        //calculateCreditForPerson(1L);
        ;
        calculateCreditFor(1L, credit -> {
            var millis = Duration.between(start, Instant.now()).toMillis();
            System.out.println("millis = " + millis);
        });

        System.out.println("Im here!");

        Thread.sleep(10_000);

       // threadPool.shutdownNow();

    }


    private static Credit calculateCreditFor(Person person) {
        var assets = getAssets(person); //IO 200
        var liabilities = getLiabilities(person); //IO

        doSomeImportantTask();
        return calculateCredit(assets, liabilities); //200
    }


    private static Credit calculateCreditForPerson(Long personId) throws ExecutionException, InterruptedException {
       return CompletableFuture.supplyAsync(() -> getPerson(personId))
               .thenComposeAsync(person -> {
            final var assetFuture = CompletableFuture.supplyAsync(() -> getAssets(person));
            final var liabilitiesFuture = CompletableFuture.supplyAsync(() -> getLiabilities(person));
            final var importantWork = CompletableFuture.runAsync(Playground::doSomeImportantTask);
           return importantWork.thenCompose((v) -> assetFuture.thenCombineAsync(liabilitiesFuture, (Playground::calculateCredit)));
       }).get();
    }

    //Using Callback
    private static void calculateCreditFor(Long personId, Consumer<Credit> callback) {
        final var outerLatch = new CountDownLatch(1);
        final var credit = new AtomicReference<Credit>();
        final var person = new AtomicReference<Person>();

        threadPool.execute(() -> {
            try {
            var personLatch = new CountDownLatch(1);
            var innerLatch = new CountDownLatch(2);

            final var assetsRef = new AtomicReference<Assets>();
            final var liabilitiesRef = new AtomicReference<Liabilities>();

            threadPool.execute(() -> getPerson(1L, (p) -> {
                person.set(p);
                personLatch.countDown();
            }));

            personLatch.await();

            threadPool.execute(() -> getAssets(person.get(), assets -> {
                assetsRef.set(assets);
                innerLatch.countDown();
            }));

            threadPool.execute(() -> getLiabilities(person.get(), liabilities -> {
                liabilitiesRef.set(liabilities);
                innerLatch.countDown();
            }));

            innerLatch.await();

            threadPool.execute(Playground::doSomeImportantTask);

            threadPool.execute(() -> calculateCredit(assetsRef.get(), liabilitiesRef.get(), (c -> {
                credit.set(c);
                outerLatch.countDown();
            })));

            outerLatch.await();

            callback.accept(credit.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }


    private static void getPerson(Long id, Consumer<Person> callback){
        sleep();
        callback.accept(new Person(id));
    }

    private static void getAssets(Person person, Consumer<Assets> callback){
        sleep();
        callback.accept(new Assets(person));
    }

    public static void getLiabilities(Person person, Consumer<Liabilities> callback) {
        sleep();
        callback.accept(new Liabilities(person));
    }

    public static void calculateCredit(Assets assets, Liabilities liabilities, Consumer<Credit> callback){

    }

    private static Credit calculateCreditForPerson3(ExecutorService pool)
            throws ExecutionException, InterruptedException {
        var person = getPerson(1L);
        Future<Assets> assetFuture = pool.submit(() -> getAssets(person));
        Future<Liabilities> liabilitiesFuture = pool.submit(() -> getLiabilities(person));

        pool.submit(() -> doSomeImportantTask());

        return calculateCredit(assetFuture.get(), liabilitiesFuture.get());
    }

    private static Credit calculateCreditForPerson2() throws InterruptedException {
        var person = getPerson(1L);
        var assetRef = new AtomicReference<Assets>();
        var t1 = new Thread(() -> assetRef.set(getAssets(person)));

        var liabilitiesRef = new AtomicReference<Liabilities>();
        var t2 = new Thread(() -> liabilitiesRef.set(getLiabilities(person)));
        var t3 = new Thread(() -> doSomeImportantTask());
        t3.start();

        t1.join();
        t2.join();

        var credit = calculateCredit(assetRef.get(), liabilitiesRef.get());

        t3.join();

        return credit;
    }

    private static void doSomeImportantTask() {
        sleep();
    }

    private static Person getPerson(Long id) {
        sleep();
        return new Person(id);
    }

    private static void sleep() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Assets getAssets(Person person) {
        sleep();
        return new Assets(person);
    }

    public static Liabilities getLiabilities(Person person) {
        sleep();
        return new Liabilities(person);
    }

    public static Credit calculateCredit(Assets assets, Liabilities liabilities) {
        sleep();
        return new Credit(assets, liabilities);
    }

    record Person(Long id) {
    }

    record Assets(Person person) {
    }

    record Liabilities(Person person) {
    }

    record Credit(Assets assets, Liabilities liabilities) {
    }

}
