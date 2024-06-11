package JAVA_projects;
/*
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    private static final int BUFFER_SIZE = 10;
    static BlockingQueue<Produit> queue = new ArrayBlockingQueue<>(BUFFER_SIZE);
    private static Producteur producteur = new Producteur(queue);
    private static Consommateur consommateur = new Consommateur(queue);

    public static void main(String[] args) {
        Frame frame = new Frame("Producer-Consumer");
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
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
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

        inputPanel.add(new Label("Product Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new Label("Product Price:"));
        inputPanel.add(priceField);
        inputPanel.add(new Label("Consume Product Name:"));
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
                                double price = Double.parseDouble(priceField.getText());
                                if (queue.remainingCapacity() == 0) {
                                    logArea.append("Le buffer est plein. Impossible de produire.\n");
                                }else if (producteur.produitExiste(name)) {
                                    EventQueue.invokeLater(() -> logArea.append("Le produit existe déjà.\n"));
                                } else if (queue.remainingCapacity() == 0) {
                                    EventQueue.invokeLater(() -> logArea.append("Le buffer est plein. Impossible de produire.\n"));
                                } else {
                                    producteur.produce(name, price);
                                    EventQueue.invokeLater(() -> logArea.append("Produit produit: " + name + " - " + price + "\n"));
                                }
                            } catch (NumberFormatException ex) {
                                EventQueue.invokeLater(() -> logArea.append("Prix invalide\n"));
                            } catch (Exception ex) {
                                EventQueue.invokeLater(() -> logArea.append("Erreur: " + ex.getMessage() + "\n"));
                            }
                        }).start();
                        break;
                    case "Consume":
                        new Thread(() -> {
                            String name = consumeNameField.getText();
                            List<Produit> produits = consommateur.getProduits();
                            if (produits.isEmpty()) {
                                EventQueue.invokeLater(() -> logArea.append("Le buffer est vide. Impossible de consommer.\n"));
                            } else {
                                consommateur.consume(name);
                                EventQueue.invokeLater(() -> {
                                    logArea.append("Produit consommé: " + name + "\n");
                                    logArea.append("Produits disponibles: ");
                                    for (Produit p : produits) {
                                        logArea.append(p.getName() + " ");
                                    }
                                    logArea.append("\n");
                                });
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

 */
// utilisation des semaphores:
/*
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Main {
    private static final int BUFFER_SIZE = 10;
    private static List<Produit> buffer = new ArrayList<>(BUFFER_SIZE);
    private static Semaphore availableSpaces = new Semaphore(BUFFER_SIZE);
    private static Semaphore availableItems = new Semaphore(0);
    private static Producteur producteur = new Producteur(buffer, availableSpaces, availableItems);
    private static Consommateur consommateur = new Consommateur(buffer, availableSpaces, availableItems);

    public static void main(String[] args) {
        Frame frame = new Frame("Producer-Consumer");
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
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
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

        inputPanel.add(new Label("Product Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new Label("Product Price:"));
        inputPanel.add(priceField);
        inputPanel.add(new Label("Consume Product Name:"));
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
                                double price = Double.parseDouble(priceField.getText());
                                if (availableSpaces != buffer.size()) {
                                    logArea.append("Le buffer est plein. Impossible de produire.\n");
                                }else if (producteur.produitExiste(name)) {
                                    EventQueue.invokeLater(() -> logArea.append("Le produit existe déjà.\n"));
                                } else if (queue.remainingCapacity() == 0) {
                                    EventQueue.invokeLater(() -> logArea.append("Le buffer est plein. Impossible de produire.\n"));
                                } else {
                                    producteur.produce(name, price);
                                    EventQueue.invokeLater(() -> logArea.append("Produit produit: " + name + " - " + price + "\n"));
                                }
                            } catch (NumberFormatException ex) {
                                EventQueue.invokeLater(() -> logArea.append("Prix invalide\n"));
                            } catch (Exception ex) {
                                EventQueue.invokeLater(() -> logArea.append("Erreur: " + ex.getMessage() + "\n"));
                            }
                        }).start();
                        break;
                    case "Consume":
                        new Thread(() -> {
                            String name = consumeNameField.getText();
                            List<Produit> produits = consommateur.getProduits();
                            if (produits.isEmpty()) {
                                EventQueue.invokeLater(() -> logArea.append("Le buffer est vide. Impossible de consommer.\n"));
                            } else {
                                consommateur.consume(name);
                                EventQueue.invokeLater(() -> logArea.append("Produit consommé: " + name + "\n"));
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
 */
// utilisation des moniteurs:
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
        Frame frame = new Frame("Producteur - Consommateur");
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

        inputPanel.add(new Label("Nom du produit:"));
        inputPanel.add(nameField);
        inputPanel.add(new Label("Prix du produit:"));
        inputPanel.add(priceField);
        inputPanel.add(new Label("Nom du produit a consommé:"));
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
                                    EventQueue.invokeLater(() -> logArea.append("Le buffer est plain. Impossible de produire.\n"));
                                }else {
                                    double price = Double.parseDouble(priceField.getText());
                                    producteur.produce(name, price);
                                    EventQueue.invokeLater(() -> logArea.append("Producteur a produit: " + name + " avec un prix de : " + price + "\n"));
                                }

                            } catch (NumberFormatException ex) {
                                EventQueue.invokeLater(() -> logArea.append("Prix invalide\n"));
                            }
                        }).start();
                        break;
                    case "Consume":
                        new Thread(() -> {
                            String name = consumeNameField.getText();
                            List<Produit> produits = consommateur.getProduits();
                            if (produits.isEmpty()) {
                                EventQueue.invokeLater(() -> logArea.append("Le buffer est vide. Impossible de consommer.\n"));
                            } else {
                                boolean produitTrouvé = produits.stream().anyMatch(produit -> produit.getName().equals(name));
                                if (produitTrouvé) {
                                    consommateur.consume(name);
                                    EventQueue.invokeLater(() -> logArea.append("Consommateur a consommé: " + name + "\n"));
                                    EventQueue.invokeLater(() ->logArea.append("Les produits disponibles:\n"));
                                    for (Produit produit : listProduits) {
                                        EventQueue.invokeLater(() ->logArea.append("- " + produit.getName() + " : " + produit.getPrice() + "\n"));
                                    }
                                }else
                                    EventQueue.invokeLater(() -> logArea.append("Le produit " + name + " n'existe pas.\n"));
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
