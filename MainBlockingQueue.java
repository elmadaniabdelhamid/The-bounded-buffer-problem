package khemal_project;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class MainBlockingQueue {
    public static void main(String[] args) {
        int bufferSize = 10;  // Default buffer size
        if (args.length > 0) {
            bufferSize = Integer.parseInt(args[0]);
        }

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(bufferSize);
        AtomicInteger producerCount = new AtomicInteger(0);
        AtomicInteger consumerCount = new AtomicInteger(0);

        GuiBlockingQueue gui = new GuiBlockingQueue(queue, producerCount, consumerCount);

        Thread producerThread = new Thread(() -> {
            while (true) {
                try {
                    if (producerCount.get() > 0) {
                        queue.put((int) (Math.random() * 100));
                        gui.log("Produced: " + queue.peek());
                        Thread.sleep(500);  // Simulate work
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
                        int item = queue.take();
                        gui.log("Consumed: " + item);
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

