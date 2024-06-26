

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiMonitors {
    private JFrame frame;
    private JTextArea textArea;
    private Queue<Integer> buffer;
    private Object bufferLock;
    private int bufferSize;
    private AtomicInteger producerCount;
    private AtomicInteger consumerCount;
    private Runnable producerTask;
    private Runnable consumerTask;
    private BufferedWriter logWriter;

    public GuiMonitors(Queue<Integer> buffer, Object bufferLock, int bufferSize, AtomicInteger producerCount, AtomicInteger consumerCount) {
        this.buffer = buffer;
        this.bufferLock = bufferLock;
        this.bufferSize = bufferSize;
        this.producerCount = producerCount;
        this.consumerCount = consumerCount;
        initializeUI();
        initializeLogWriter();
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
            log("Added producer. Total producers: " + producerCount.get());
            new Thread(producerTask).start();
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
            new Thread(consumerTask).start();
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

    private void initializeLogWriter() {
        try {
            logWriter = new BufferedWriter(new FileWriter("journal.txt", true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String message) {
        SwingUtilities.invokeLater(() -> textArea.append(message + "\n"));
        logToFile(message);
    }

    public void logBufferFull() {
        String message = "Buffer is full";
        SwingUtilities.invokeLater(() -> textArea.append(message + "\n"));
        logToFile(message);
    }

    public void logBufferEmpty() {
        String message = "Buffer is empty";
        SwingUtilities.invokeLater(() -> textArea.append(message + "\n"));
        logToFile(message);
    }

    private void logToFile(String message) {
        try {
            logWriter.write(message);
            logWriter.newLine();
            logWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setProducerTask(Runnable producerTask) {
        this.producerTask = producerTask;
    }

    public void setConsumerTask(Runnable consumerTask) {
        this.consumerTask = consumerTask;
    }
}
