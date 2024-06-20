import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class MainSemaphores {
    public static void main(String[] args) {
        final int bufferSize = 10;  // Default buffer size

        Queue<Integer> buffer = new LinkedList<>();
        Semaphore bufferLock = new Semaphore(1);
        Semaphore items = new Semaphore(0);
        Semaphore spaces = new Semaphore(bufferSize);

        AtomicInteger producerCount = new AtomicInteger(0);
        AtomicInteger consumerCount = new AtomicInteger(0);

        GuiSemaphores gui = new GuiSemaphores(buffer, bufferLock, items, spaces, producerCount, consumerCount);

        Thread producerThread = new Thread(() -> {
            while (true) {
                try {
                    if (producerCount.get() > 0 ) {
                        spaces.acquire();
                        bufferLock.acquire();
                        int item = (int) (Math.random() * 100);
                        buffer.add(item);
                        gui.log("Produced: " + item);
                        bufferLock.release();
                        items.release();
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
                    if (consumerCount.get() > 0 && !buffer.isEmpty()) {
                        items.acquire();
                        bufferLock.acquire();
                        int item = buffer.remove();
                        gui.log("Consumed: " + item);
                        bufferLock.release();
                        spaces.release();
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


