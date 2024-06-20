//package The-bounded-buffer-problem;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class MainBlockingQueue {
    public static void main(String[] args) {
        final int bufferSize = 10;  // Default buffer size

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(bufferSize);
        AtomicInteger producerCount = new AtomicInteger(0);
        AtomicInteger consumerCount = new AtomicInteger(0);

        CuiBlockingQueue gui = new CuiBlockingQueue(queue, producerCount, consumerCount);

        Thread producerThread = new Thread(() -> {
            while (true) {
                try {
                    if (producerCount.get() > 0) {
                        queue.put((int) (Math.random()*100));
                        gui.log("Produced: " + queue.peek());
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread consumerThread = new Thread(() -> {
            while (true) {
                try {
                    if (consumerCount.get() > 0) {
                        //int item = queue.take();
                        gui.log("Consumed: " +  queue.take());
                        Thread.sleep(500);  // Simulate work
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        producerThread.start();
        consumerThread.start();
    }
}

