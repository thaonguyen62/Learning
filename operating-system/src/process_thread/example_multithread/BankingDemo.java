package process_thread.example_multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BankingDemo {

    public static void main(String[] args) throws InterruptedException {
            Account acc1 = new Account(1, 1000);
            Account acc2 = new Account(2, 1000);
            Account acc3 = new Account(3, 1000);
            Bank bank = new Bank();

            ExecutorService pool = Executors.newFixedThreadPool(5);

            // Nhiều thread transfer đồng thời
            pool.submit(() -> bank.transfer(acc1, acc2, 200));
        pool.submit(() -> acc1.deposit(500));
        pool.submit(() -> bank.transfer(acc2, acc1, 150)); // ngược chiều → deadlock nếu không fix
        pool.submit(() -> bank.transfer(acc2, acc3, 300));
        pool.submit(() -> bank.transfer(acc3, acc1, 100));
            pool.submit(() -> acc2.withdraw(800));
            pool.submit(() -> bank.transfer(acc1, acc3, 200));
            pool.submit(() -> bank.transfer(acc3, acc2, 50));

            pool.shutdown();
            pool.awaitTermination(5, TimeUnit.SECONDS);

            // Kiểm tra tổng số dư không đổi (1000+1000+1000 = 3000)
            double total = acc1.getBalance() + acc2.getBalance() + acc3.getBalance();
            System.out.println("\n=== KẾT QUẢ ===");
            System.out.printf("Account 1: %.0f%n", acc1.getBalance());
            System.out.printf("Account 2: %.0f%n", acc2.getBalance());
            System.out.printf("Account 3: %.0f%n", acc3.getBalance());
            System.out.printf("Tổng: %.0f (đúng phải là 3500 vì deposit 500)%n", total);
        }

}
