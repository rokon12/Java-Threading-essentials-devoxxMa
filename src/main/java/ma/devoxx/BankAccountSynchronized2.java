package ma.devoxx;

public class BankAccountSynchronized2 {
    private double balance;
    private final Object lock = new Object();

    public BankAccountSynchronized2(double initial) {
        this.balance = initial;
    }

    public void deposit(double amount) {
        synchronized (lock) {
            balance = balance + amount;
        }
    }

    public synchronized void withdraw(double amount) {
        synchronized (lock) {
            balance = balance - amount;
        }
    }

    public double getBalance() {
        synchronized (lock) {
            return balance;
        }
    }
}
