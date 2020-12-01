package task6;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public abstract class AbstractFixnumLock implements FixnumLock {

    private ConcurrentMap<Thread, Integer> idMap;
    private int maxNumberOfThreads;

    public AbstractFixnumLock(int maxNumberOfThreads) {
        this.maxNumberOfThreads = maxNumberOfThreads;
        this.idMap = new ConcurrentHashMap<>();
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
    public synchronized boolean register() {

        if (idMap.containsKey(Thread.currentThread()) || idMap.size() >= maxNumberOfThreads) {
            return false;
        }

        Set<Integer> set = new HashSet<>(idMap.values());
        int position = -1;
        for (int i = 0; i < maxNumberOfThreads; i++) {
            if (!set.contains(i)) {
                position = i;
                break;
            }
        }

        idMap.put(Thread.currentThread(), position);
        return true;
    }

    @Override
    public void unregister() {
        Thread currentThread = Thread.currentThread();
        idMap.remove(currentThread);
    }

    public void reset(){
        idMap.clear();
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
