package process_thread.example_multithread;

public class Bank {
    public void transfer(Account from, Account to, double amount) {
        Account first  = from.getId() < to.getId() ? from : to;
        Account second = from.getId() < to.getId() ? to : from;

        first.lock.lock();
        try {
            second.lock.lock();
            try {
                if (from.getBalance() < amount) {
                    System.out.printf("[Transfer] Account %d → %d FAILED (insufficient)%n",
                            from.getId(), to.getId());
                    return;
                }
                System.out.printf("[Transfer] Account %d → %d : %.0f%n",
                        from.getId(), to.getId(), amount);
            } finally {
                second.lock.unlock();
            }
        } finally {
            first.lock.unlock();
        }

        // Dùng withdraw + deposit sau khi đã lock xong
        from.withdraw(amount);
        to.deposit(amount);
    }
}
