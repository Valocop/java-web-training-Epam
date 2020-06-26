package by.training.module4.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class PortPool<T, R> implements Cloneable, Serializable {
    private static final Logger LOG = LogManager.getLogger();
    private static final long serialVersionUID = -234234234235235L;
    private static final Lock staticLock = new ReentrantLock();
    private static PortPool SINGLE_INSTANCE = null;
    private static final AtomicBoolean PRESENT = new AtomicBoolean(false);

    private final Lock lock = new ReentrantLock();
    private final Queue<T> resources = new LinkedList<>();
    private final List<R> items = new ArrayList<>();
    private int capacity;
    private Semaphore semaphore;

    public void init(Queue<T> resources, List<R> items, int capacity) throws ResourceException {
        if (resources == null || items == null || capacity <= 0 || items.size() > capacity) {
            throw new ResourceException("Incorrect params.");
        }
        this.resources.addAll(resources);
        this.items.addAll(items);
        this.capacity = capacity;
        semaphore = new Semaphore(this.resources.size(), true);
    }

    public static <T, R> PortPool<T, R> getInstance() {
        if (!PRESENT.get()) {
            staticLock.lock();
            if (SINGLE_INSTANCE == null) {
                SINGLE_INSTANCE = new PortPool();
            }
            PRESENT.set(true);
            staticLock.unlock();
        }
        return SINGLE_INSTANCE;
    }

    public T getResource() throws ResourceException {
        try {
            semaphore.acquire();
            lock.lock();
            T resource = resources.poll();
            lock.unlock();
            return resource;
        } catch (InterruptedException e) {
            throw new ResourceException(e);
        }
    }

    public void returnResource(T res) {
        lock.lock();
        resources.offer(res);
        lock.unlock();
        semaphore.release();
    }

    public List<R> getItems(int count) throws ResourceException {
        lock.lock();
        List<R> getItems;
        if (count > items.size()) {
            lock.unlock();
            throw new ResourceException("Port does not have so much containers " + count);
        }
        getItems = new ArrayList<>(items.subList(0, count));
        items.removeAll(getItems);
        LOG.info("Port give " + count + " to the ship.");
        LOG.info("Port have " + items.size() + " containers.");
        try {
            TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
            lock.unlock();
            throw new ResourceException(e);
        }
        lock.unlock();
        return getItems;
    }

    public void createItems(List<R> createItems) throws ResourceException {
        lock.lock();
        if (createItems.size() + items.size() > capacity) {
            lock.unlock();
            throw new ResourceException("Port is crowded to receive " + createItems.size() + " containers.");
        }
        items.addAll(createItems);
        LOG.info("Port receive " + createItems.size() + " containers.");
        LOG.info("Port have " + items.size() + " containers.");
        try {
            TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
            lock.unlock();
            throw new ResourceException(e);
        }
        lock.unlock();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        if (SINGLE_INSTANCE != null) {
            throw new CloneNotSupportedException();
        }
        return super.clone();
    }

    protected Object readResolve() {
        return SINGLE_INSTANCE;
    }
}
