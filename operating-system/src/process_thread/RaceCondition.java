package process_thread;

public class RaceCondition {
    static int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {counter++;}
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {counter++;}
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(counter);
    }
}
