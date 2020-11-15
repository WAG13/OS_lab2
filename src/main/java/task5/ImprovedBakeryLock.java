package task5;

import task6.AbstractFixnumLock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class ImprovedBakeryLock extends AbstractFixnumLock {

    private static AtomicInteger currTicket;
    private AtomicIntegerArray tickets;

    public ImprovedBakeryLock(int maxNumberOfThreads) {
        super(maxNumberOfThreads);
        tickets = new AtomicIntegerArray(maxNumberOfThreads);
        currTicket = new AtomicInteger(0);
    }

    @Override
    public void lock() {
        tickets.set(getId(), currTicket.incrementAndGet());

        for (int i = 0; i < tickets.length(); ++i){
            if (i != getId()){
                while ( tickets.get(i) != 0 && (tickets.get(getId()) > tickets.get(i))){
                    Thread.yield();
                }
            }
        }

    }

    @Override
    public void unlock() { tickets.set(getId(), 0); }

}