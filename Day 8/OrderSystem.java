import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class OrderProcessor implements Runnable {
    private BlockingQueue<String> orderQueue;
    private volatile boolean running = true;

    public OrderProcessor(BlockingQueue<String> orderQueue) {
        this.orderQueue = orderQueue;
    }

    @Override
    public void run() {
        while (running || !orderQueue.isEmpty()) {
            try {
                String order = orderQueue.take();
                System.out.println("Processing order: " + order);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void shutdown() {
        running = false;
    }
}

public class OrderSystem {
    public static void main(String[] args) {
        BlockingQueue<String> orderQueue = new LinkedBlockingQueue<>();
        OrderProcessor processor = new OrderProcessor(orderQueue);
        Thread processorThread = new Thread(processor);
        processorThread.start();

        for (int i = 1; i <= 5; i++) {
            orderQueue.offer("Order " + i);
        }

        processor.shutdown();
    }
}
