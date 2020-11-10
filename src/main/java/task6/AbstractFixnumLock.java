package task6;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;

public abstract class AbstractFixnumLock implements FixnumLock {

    private AtomicInteger counter;
    private ConcurrentMap<Thread, Integer> idMap;
    private int maxNumberOfThreads;

    public AbstractFixnumLock(int maxNumberOfThreads) {
        this.maxNumberOfThreads = maxNumberOfThreads;
        this.counter = new AtomicInteger(0);
        this.idMap = new ConcurrentHashMap<Thread, Integer>();
    }

    public int getMaxNumberOfThreads() {
        return maxNumberOfThreads;
    }

    @Override
    public int getId() {
        Integer id = idMap.get(Thread.currentThread());
        if (id == null) {
            throw new IllegalStateException("Thread must be registered in order to use this lock");
        }
        return id;
    }

    @Override
    public boolean register() {
        if (counter.get() == maxNumberOfThreads) {
            return false;
        }
        idMap.put(Thread.currentThread(), counter.getAndIncrement());
        return true;
    }

    @Override
    public synchronized void unregister() {
        Thread currentThread = Thread.currentThread();
        int removedId = idMap.get(currentThread);
        idMap.remove(currentThread);

        // reindexing
        for (Map.Entry<Thread, Integer> entry : idMap.entrySet()) {
            int value = entry.getValue();
            if (value > removedId) {
                entry.setValue(value - 1);
            }
        }
        counter.decrementAndGet();
    }


    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
