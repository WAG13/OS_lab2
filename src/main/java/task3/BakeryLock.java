package task3;

import task6.AbstractFixnumLock;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class BakeryLock extends AbstractFixnumLock {
    AtomicIntegerArray picking;
    AtomicIntegerArray tickets;

    public BakeryLock(int maxNumberOfThreads) {
        super(maxNumberOfThreads);
        picking = new AtomicIntegerArray(maxNumberOfThreads);
        tickets = new AtomicIntegerArray(maxNumberOfThreads);
    }

    @Override
    public void lock() {
        final int maxNumOfThreads = getMaxNumberOfThreads();
        int pid = getId();
        picking.set(pid, 1);

        int max = 0;
        for (int i = 0; i < maxNumOfThreads; i++)
        {
            int current = tickets.get(i);
            if (current > max) {
                max = current;
            }
        }

        tickets.set(pid, max + 1);
        picking.set(pid, 0);

        for (int i = 0; i < maxNumOfThreads; i++) {
            if (i != pid) {
                while (picking.get(i) == 1) {
                    Thread.yield();
                }
                while (tickets.get(i) != 0 && (tickets.get(pid) > tickets.get(i) ||
                                               (tickets.get(pid) == tickets.get(i) && pid > i))) {
                    Thread.yield();
                }
            }
        }
    }

    @Override
    public void unlock() {
        tickets.set(getId(), 0);
    }
}
