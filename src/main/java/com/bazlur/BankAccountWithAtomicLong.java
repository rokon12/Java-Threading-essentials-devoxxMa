package com.bazlur;

import java.util.concurrent.atomic.LongAdder;

public class BankAccountWithAtomicLong {
    private final LongAdder balance;

    public BankAccountWithAtomicLong(long initialBalance) {
        this.balance = new LongAdder();
        this.balance.add(initialBalance);
    }

    public void deposit(long amount) {
       this.balance.add(amount);
    }

    public void withdraw(long amount) {
        this.deposit(-amount);
    }

    public long getBalance() {
        return balance.longValue();
    }
}
