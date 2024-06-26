

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class MainMonitors {
    public static void main(String[] args) {
        final int bufferSize = 10;

        Queue<Integer> buffer = new LinkedList<>();
        Object bufferLock = new Object();

        AtomicInteger producerCount = new AtomicInteger(0);
        AtomicInteger consumerCount = new AtomicInteger(0);

        GuiMonitors gui = new GuiMonitors(buffer, bufferLock, bufferSize, producerCount, consumerCount);

        Runnable producerTask = createProducerTask(buffer, bufferLock, bufferSize, gui, producerCount);
        Runnable consumerTask = createConsumerTask(buffer, bufferLock, gui, consumerCount);

        gui.setProducerTask(producerTask);
        gui.setConsumerTask(consumerTask);
    }

    private static Runnable createProducerTask(Queue<Integer> buffer, Object bufferLock, int bufferSize, GuiMonitors gui, AtomicInteger producerCount) {
        return () -> {
            while (true) {
                try {
                    if (producerCount.get() > 0) {
                        synchronized (bufferLock) {
                            while (buffer.size() == bufferSize) {
                                bufferLock.wait();
                            }
                            int item = (int) (Math.random() * 100);
                            buffer.add(item);
                            gui.log("Produced: " + item + " by thread ID: " + Thread.currentThread().getId());
                            if (buffer.size() == bufferSize) {
                                gui.logBufferFull();
                            }
                            bufferLock.notifyAll();
                        }
                        Thread.sleep(500);  // Simulate work
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
    }

    private static Runnable createConsumerTask(Queue<Integer> buffer, Object bufferLock, GuiMonitors gui, AtomicInteger consumerCount) {
        return () -> {
            while (true) {
                try {
                    if (consumerCount.get() > 0) {
                        synchronized (bufferLock) {
                            while (buffer.isEmpty()) {
                                bufferLock.wait();
                            }
                            int item = buffer.remove();
                            gui.log("Consumed: " + item + " by thread ID: " + Thread.currentThread().getId());
                            if (buffer.isEmpty()) {
                                gui.logBufferEmpty();
                            }
                            bufferLock.notifyAll();
                        }
                        Thread.sleep(500);  // Simulate work
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
    }
}
