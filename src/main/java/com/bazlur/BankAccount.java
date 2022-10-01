package com.bazlur;

public class BankAccount {
    private long balance;

    public BankAccount(long initialBalance) {
        this.balance = initialBalance;
    }

    public  void deposit(long amount) {
        this.balance += amount;
    }

    public void withdraw(long amount) {
        this.deposit(-amount);
    }

    public long getBalance() {
        return balance;
    }
}
