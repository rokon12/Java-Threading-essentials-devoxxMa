package ma.devoxx;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private double balance;

    private final Lock lock = new ReentrantLock();

    public BankAccount(double initial) {
        this.balance = initial;
    }

    public void deposit(double amount) {
        lock.lock();
        try {
            balance = balance + amount;
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(double amount) {
        lock.lock();
        try {
            balance = balance - amount;
        } finally {
            lock.unlock();
        }
    }

    public double getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }
}
