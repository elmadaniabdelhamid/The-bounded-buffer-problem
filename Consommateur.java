package JAVA_projects;
import java.util.ArrayList;
import java.util.List;

public class Consommateur {
    private final List<Produit> queue;
    private final Object lock;

    public Consommateur(List<Produit> queue, Object lock) {
        this.queue = queue;
        this.lock = lock;
    }

    public void consume(String name) {
        synchronized (lock) {
            while (queue.isEmpty()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            boolean produitTrouvé = false;
            for (Produit produit : queue) {
                if (produit.getName().equals(name)) {
                    queue.remove(produit);
                    produitTrouvé = true;
                    lock.notifyAll();
                    break;
                }
            }
        }
    }

    public List<Produit> getProduits() {
        synchronized (lock) {
            return new ArrayList<>(queue);
        }
    }
}
