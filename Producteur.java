package JAVA_projects;
/*
import java.util.concurrent.BlockingQueue;

public class Producteur {
    private BlockingQueue<Produit> queue;

    public Producteur(BlockingQueue<Produit> queue) {
        this.queue = queue;
    }

    public boolean produitExiste(String name) {
        for (Produit produit : queue) {
            if (produit.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void produce(String name, double price) {
        try {
            queue.put(new Produit(name, price));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

 */
// semaphores:
 /*
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Producteur {
    private List<Produit> buffer;
    private Semaphore availableSpaces;
    private Semaphore availableItems;
    private final Object lock = new Object();

    public Producteur(List<Produit> buffer, Semaphore availableSpaces, Semaphore availableItems) {
        this.buffer = buffer;
        this.availableSpaces = availableSpaces;
        this.availableItems = availableItems;
    }

    public boolean produitExiste(String name) {
        synchronized (lock) {
            for (Produit produit : buffer) {
                if (produit.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void produce(String name, double price) {
        try {
            availableSpaces.acquire();
            synchronized (lock) {
                buffer.add(new Produit(name, price));
            }
            availableItems.release();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

  */
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

