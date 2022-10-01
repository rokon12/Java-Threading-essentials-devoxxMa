package ma.devoxx;

public class BankAccountSynchronized {
    private double balance;

    public BankAccountSynchronized(double initial) {
        this.balance = initial;
    }

    public synchronized void deposit(double amount){
        balance = balance + amount;
    }

    public synchronized void withdraw(double amount){
        balance = balance - amount;
    }

    public synchronized  double getBalance() {
        return balance;
    }
}
