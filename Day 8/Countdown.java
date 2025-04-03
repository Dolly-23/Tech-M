import java.util.concurrent.*;

public class Countdown{
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 3; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {}
                latch.countDown();
            });
        }

        latch.await();
        System.out.println("All tasks finished.");
        executor.shutdown();
    }
}
