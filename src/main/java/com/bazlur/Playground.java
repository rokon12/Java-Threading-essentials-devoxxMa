package com.bazlur;

public class Playground {
    public static void main(String[] args) throws InterruptedException {

        var account = new BankAccountWithLock(100);

        var t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                account.withdraw(5);
            }
        });

        var t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                account.deposit(5);
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("account = " + account.getBalance());
    }
}
