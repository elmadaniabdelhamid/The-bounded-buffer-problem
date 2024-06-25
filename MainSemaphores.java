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

        // Producer thread creation logic
        Runnable producerTask = () -> {
            while (true) {
                try {
                    if (producerCount.get() > 0) {
                        spaces.acquire();
                        bufferLock.acquire();
                        int item = (int) (Math.random() * 100);
                        buffer.add(item);
                        gui.log("Produced: " + item + " by Producer : " + Thread.currentThread().getId());
                        if (buffer.size() == bufferSize) {
                            gui.logBufferFull();
                        }
                        bufferLock.release();
                        items.release();
                        Thread.sleep(500);  // Simulate work
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        // Consumer thread creation logic
        Runnable consumerTask = () -> {
            while (true) {
                try {
                    if (consumerCount.get() > 0) {
                        items.acquire();
                        bufferLock.acquire();
                        if (!buffer.isEmpty()) {
                            int item = buffer.remove();
                            gui.log("Consumed: " + item + " by Consumer : " + Thread.currentThread().getId());
                        }
                        if (buffer.isEmpty()) {
                            gui.logBufferEmpty();
                        }
                        bufferLock.release();
                        spaces.release();
                        Thread.sleep(500);  // Simulate work
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        // Initial thread creation for GUI responsiveness
        gui.setProducerTask(producerTask);
        gui.setConsumerTask(consumerTask);
    }
}
