package JAVA_projects;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int BUFFER_SIZE = 5;
    private static final List<Produit> listProduits = new ArrayList<>();
    private static final Object lock = new Object();
    private static final Producteur producteur = new Producteur(listProduits, BUFFER_SIZE, lock);
    private static final Consommateur consommateur = new Consommateur(listProduits, lock);

    public static void main(String[] args) {
        // creation de d'une frame :
        Frame frame = new Frame("Producer - Consumer");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout(10, 10));
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        // Log area
        TextArea logArea = new TextArea(10, 40);
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.BOLD, 12));
        frame.add(logArea, BorderLayout.CENTER);

        // Input panel
        Panel inputPanel = new Panel();
        inputPanel.setLayout(new FlowLayout());

        TextField nameField = new TextField(10);
        TextField priceField = new TextField(10);
        TextField consumeNameField = new TextField(10);

        Choice actionChoice = new Choice();
        actionChoice.add("Produce");
        actionChoice.add("Consume");
        inputPanel.add(actionChoice);

        inputPanel.add(new Label("Product name :"));
        inputPanel.add(nameField);
        inputPanel.add(new Label("Product price :"));
        inputPanel.add(priceField);
        inputPanel.add(new Label("Consume Product name :"));
        inputPanel.add(consumeNameField);

        frame.add(inputPanel, BorderLayout.NORTH);

        // Button panel
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout());

        Button actionButton = new Button("Perform Action");
        Button exitButton = new Button("Exit");

        actionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String action = actionChoice.getSelectedItem();
                switch (action) {
                    case "Produce":
                        new Thread(() -> {
                            try {
                                String name = nameField.getText();
                                List<Produit> produits = consommateur.getProduits();
                                if (produits.size() == BUFFER_SIZE) {
                                    EventQueue.invokeLater(() -> logArea.append("The buffer is full . Impossible to produce!\n"));
                                }else {
                                    double price = Double.parseDouble(priceField.getText());
                                    producteur.produce(name, price);
                                    EventQueue.invokeLater(() -> logArea.append("Producer produce : " + name + " with a price of : " + price + "\n"));
                                }

                            } catch (NumberFormatException ex) {
                                EventQueue.invokeLater(() -> logArea.append("Invalide price\n"));
                            }
                        }).start();
                        break;
                    case "Consume":
                        new Thread(() -> {
                            String name = consumeNameField.getText();
                            List<Produit> produits = consommateur.getProduits();
                            if (produits.isEmpty()) {
                                EventQueue.invokeLater(() -> logArea.append("The buffer is empty. Impossible to Consume.\n"));
                            } else {
                                boolean produitTrouvé = produits.stream().anyMatch(produit -> produit.getName().equals(name));
                                if (produitTrouvé) {
                                    consommateur.consume(name);
                                    EventQueue.invokeLater(() -> logArea.append("Consumer consume: " + name + "\n"));
                                    EventQueue.invokeLater(() ->logArea.append("The exesting products:\n"));
                                    for (Produit produit : listProduits) {
                                        EventQueue.invokeLater(() ->logArea.append("- " + produit.getName() + " : " + produit.getPrice() + "\n"));
                                    }
                                }else
                                    EventQueue.invokeLater(() -> logArea.append("The product " + name + " doesn't existe.\n"));
                                }
                        }).start();
                        break;
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        actionChoice.addItemListener(e -> {
            String action = actionChoice.getSelectedItem();
            switch (action) {
                case "Produce":
                    nameField.setEnabled(true);
                    priceField.setEnabled(true);
                    consumeNameField.setEnabled(false);
                    break;
                case "Consume":
                    nameField.setEnabled(false);
                    priceField.setEnabled(false);
                    consumeNameField.setEnabled(true);
                    break;
            }
        });

        actionChoice.select(0);
        // Trigger initial selection
        actionChoice.getItemListeners()[0].itemStateChanged(null);

        buttonPanel.add(actionButton);
        buttonPanel.add(exitButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
