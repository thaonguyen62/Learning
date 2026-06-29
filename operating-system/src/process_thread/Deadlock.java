package process_thread;

public class Deadlock {
    static final Object lockA = new Object();
    static final Object lockB = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("T1 giữ lockA, chờ lockB...");
                try { Thread.sleep(100); } catch (Exception e) {}

                synchronized (lockB) {  // chờ mãi vì T2 đang giữ lockB
                    System.out.println("T1 xong");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lockB) {
                System.out.println("T2 giữ lockB, chờ lockA...");
                try { Thread.sleep(100); } catch (Exception e) {}

                synchronized (lockA) {  // chờ mãi vì T1 đang giữ lockA
                    System.out.println("T2 xong");
                }
            }
        });

        t1.start();
        t2.start();

        Thread.sleep(3000);
        System.out.println("\n--- Kiểm tra sau 3 giây ---");
        System.out.println("T1 state: " + t1.getState());
        System.out.println("T2 state: " + t2.getState());

        var bean = java.lang.management.ManagementFactory.getThreadMXBean();
        long[] deadlocked = bean.findDeadlockedThreads();
        if (deadlocked != null) {
            System.out.println("DEADLOCK! Các thread bị kẹt:");
            for (var info : bean.getThreadInfo(deadlocked)) {
                System.out.println("  → " + info.getThreadName()
                        + " đang chờ lock giữ bởi: " + info.getLockOwnerName());
            }
        }
    }
}
