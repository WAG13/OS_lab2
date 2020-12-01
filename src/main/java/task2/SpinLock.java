package task2;

import task6.AbstractFixnumLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;

public class SpinLock extends AbstractFixnumLock {
    private final AtomicInteger indicator;

    public SpinLock(int maxNumberOfThreads) {
        super(maxNumberOfThreads);
        indicator = new AtomicInteger(0);
    }

    @Override
    public void lock() {
        getId();
        while (!indicator.compareAndSet(0, 1));
    }

    @Override
    public void unlock() {
        indicator.set(0);
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
