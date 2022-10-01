package com.bazlur;

import java.util.concurrent.locks.Lock;

public class BankAccountWithLock {
    private long balance;
    private final Object lock = new Object();

    public BankAccountWithLock(long initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(long amount) {
        synchronized (lock) {
            this.balance += amount;
        }
    }

    public void withdraw(long amount) {
        synchronized (lock) {
            this.balance -= amount;
        }
    }

    public long getBalance() {
        return balance;
    }
}
