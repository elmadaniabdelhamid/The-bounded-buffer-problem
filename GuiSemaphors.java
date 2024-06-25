import javax.swing.*;
import java.awt.*;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiSemaphores {
    private JFrame frame;
    private JTextArea textArea;
    private Queue<Integer> buffer;
    private Semaphore bufferLock;
    private Semaphore items;
    private Semaphore spaces;
    private AtomicInteger producerCount;
    private AtomicInteger consumerCount;

    private Runnable producerTask;
    private Runnable consumerTask;

    public GuiSemaphores(Queue<Integer> initialBuffer, Semaphore bufferLock, Semaphore items, Semaphore spaces, AtomicInteger producerCount, AtomicInteger consumerCount) {
        this.buffer = initialBuffer;
        this.bufferLock = bufferLock;
        this.items = items;
        this.spaces = spaces;
        this.producerCount = producerCount;
        this.consumerCount = consumerCount;
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Producer Consumer Simulation");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new JTextArea(15, 30);
        textArea.setEditable(false);

        JPanel panel = new JPanel();
        JButton addProducer = new JButton("Add Producer");
        JButton removeProducer = new JButton("Remove Producer");
        JButton addConsumer = new JButton("Add Consumer");
        JButton removeConsumer = new JButton("Remove Consumer");

        addProducer.addActionListener(e -> {
            producerCount.incrementAndGet();
            new Thread(producerTask).start();
            log("Added producer. Total producers: " + producerCount.get());
        });

        removeProducer.addActionListener(e -> {
            if (producerCount.get() > 0) {
                producerCount.decrementAndGet();
                log("Removed producer. Total producers: " + producerCount.get());
            }
        });

        addConsumer.addActionListener(e -> {
            consumerCount.incrementAndGet();
            new Thread(consumerTask).start();
            log("Added consumer. Total consumers: " + consumerCount.get());
        });

        removeConsumer.addActionListener(e -> {
            if (consumerCount.get() > 0) {
                consumerCount.decrementAndGet();
                log("Removed consumer. Total consumers: " + consumerCount.get());
            }
        });

        panel.add(addProducer);
        panel.add(removeProducer);
        panel.add(addConsumer);
        panel.add(removeConsumer);

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public void log(String message) {
        SwingUtilities.invokeLater(() -> textArea.append(message + "\n"));
    }
    public void logBufferFull() {
        SwingUtilities.invokeLater(() -> textArea.append("Buffer is full\n"));
    }

    public void logBufferEmpty() {
        SwingUtilities.invokeLater(() -> textArea.append("Buffer is empty\n"));
    }

    public void setProducerTask(Runnable producerTask) {
        this.producerTask = producerTask;
    }

    public void setConsumerTask(Runnable consumerTask) {
        this.consumerTask = consumerTask;
    }
}
