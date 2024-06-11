package JAVA_projects;
import java.util.List;

public class Producteur {
    private final List<Produit> queue;
    private final int maxSize;
    private final Object lock;

    public Producteur(List<Produit> queue, int maxSize, Object lock) {
        this.queue = queue;
        this.maxSize = maxSize;
        this.lock = lock;
    }

/*
if you wanna check if a product is already existe:
    public boolean produitExiste(String name) {
        synchronized (lock) {
            for (Produit produit : queue) {
                if (produit.getName().equals(name)) {
                    return true;
                }
            }
            return false;
        }
    }

 */

    public void produce(String name, double price ) {
        synchronized (lock) {
            while (queue.size() == maxSize) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            queue.add(new Produit(name, price));
            lock.notifyAll();
        }
    }
}

