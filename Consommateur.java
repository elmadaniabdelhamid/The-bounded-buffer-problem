package JAVA_projects;
/*
import java.util.concurrent.BlockingQueue;
import java.util.ArrayList;
import java.util.List;
public class Consommateur {
    private BlockingQueue<Produit> queue;
    public Consommateur(BlockingQueue<Produit> queue) {
        this.queue = queue;
    }
    public void consume(String name) {
        try {
            for (Produit produit : queue) {
                if (produit.getName().equals(name)) {
                    queue.remove(produit);
                    break;
                }
                System.out.println("produit n'existe pas");
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
    public List<Produit> getProduits() {
        return new ArrayList<>(queue);
    }
}

 */
// semaphores:
/*
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Consommateur {
    private List<Produit> buffer;
    private Semaphore availableSpaces;
    private Semaphore availableItems;
    private final Object lock = new Object();

    public Consommateur(List<Produit> buffer, Semaphore availableSpaces, Semaphore availableItems) {
        this.buffer = buffer;
        this.availableSpaces = availableSpaces;
        this.availableItems = availableItems;
    }

    public void consume(String name) {
        try {
            availableItems.acquire();
            synchronized (lock) {
                for (Produit produit : buffer) {
                    if (produit.getName().equals(name)) {
                        buffer.remove(produit);
                        break;
                    }
                }
            }
            availableSpaces.release();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public List<Produit> getProduits() {
        synchronized (lock) {
            return new ArrayList<>(buffer);
        }
    }
}
*/
//using monitors:
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