package khemal_project;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiBlockingQueue {
    private JFrame frame;
    private JTextArea textArea;
    private BlockingQueue<Integer> queue;
    private AtomicInteger producerCount;
    private AtomicInteger consumerCount;
    private JTextField bufferSizeField;

    public GuiBlockingQueue(BlockingQueue<Integer> initialQueue, AtomicInteger producerCount, AtomicInteger consumerCount) {
        this.queue = initialQueue;
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
        JButton updateBufferSize = new JButton("Update Buffer Size");

        bufferSizeField = new JTextField(5);
        bufferSizeField.setText(Integer.toString(queue.size()));  // Set initial size

        addProducer.addActionListener(e -> {
            producerCount.incrementAndGet();
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
            log("Added consumer. Total consumers: " + consumerCount.get());
        });

        removeConsumer.addActionListener(e -> {
            if (consumerCount.get() > 0) {
                consumerCount.decrementAndGet();
                log("Removed consumer. Total consumers: " + consumerCount.get());
            }
        });

        updateBufferSize.addActionListener(e -> {
            int newSize = Integer.parseInt(bufferSizeField.getText());
            updateBufferSize(newSize);
        });

        panel.add(addProducer);
        panel.add(removeProducer);
        panel.add(addConsumer);
        panel.add(removeConsumer);
        panel.add(new JLabel("Buffer Size:"));
        panel.add(bufferSizeField);
        panel.add(updateBufferSize);

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void updateBufferSize(int newSize) {
        BlockingQueue<Integer> newQueue = new LinkedBlockingQueue<>(newSize);
        queue.drainTo(newQueue);
        queue = newQueue;
        log("Buffer size updated to: " + newSize);
    }

    public void log(String message) {
        SwingUtilities.invokeLater(() -> textArea.append(message + "\n"));
    }
}
