package process_thread.example_multithread;

import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private final int id;
    private double balance;
    final ReentrantLock lock = new ReentrantLock();

    public Account(int id, double initialBalance) {
        this.id = id;
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        lock.lock();
        try {
            balance += amount;
            System.out.printf("[Account %d] Deposit %.0f → Balance: %.0f%n", id, amount, balance);
        } finally {
            lock.unlock();
        }
    }

    public boolean withdraw(double amount) {
        lock.lock();
        try {
            if (balance < amount) {
                System.out.printf("[Account %d] Withdraw %.0f FAILED (insufficient)%n", id, amount);
                return false;
            }
            balance -= amount;
            System.out.printf("[Account %d] Withdraw %.0f → Balance: %.0f%n", id, amount, balance);
            return true;
        } finally {
            lock.unlock();
        }
    }

    public double getBalance() {
        lock.lock();
        try { return balance; }
        finally { lock.unlock(); }
    }

    public int getId() { return id;}
}
