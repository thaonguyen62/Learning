package process_thread;

public class Stavation {
    static final Object lock = new Object();

    public static void main(String[] args) {
        for (int i = 1; i <= 5; i++) {
            Thread highPriority = new Thread(() -> {
                while (true) {
                    synchronized (lock) {
                        System.out.println(Thread.currentThread().getName() + " đang chạy");
                        try { Thread.sleep(10); } catch (Exception e) {}
                    }
                }
            }, "High-" + i);
            highPriority.setPriority(Thread.MAX_PRIORITY); // priority 10
            highPriority.start();
        }

        Thread lowPriority = new Thread(() -> {
            System.out.println("Low thread: đang chờ cơ hội...");
            synchronized (lock) {
                System.out.println("Low thread: CUỐI CÙNG CŨNG CHẠY ĐƯỢC!");
            }
        }, "Low-Priority");
        lowPriority.setPriority(Thread.MIN_PRIORITY); // priority 1
        lowPriority.start();
    }
}
