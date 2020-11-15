package task5;

import task6.AbstractFixnumLock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class ImprovedBakeryLock extends AbstractFixnumLock {

    private static AtomicInteger currTicket;
    private static final int MAX = Integer.MAX_VALUE;
    private AtomicIntegerArray tickets;

    public ImprovedBakeryLock(int maxNumberOfThreads) {
        super(maxNumberOfThreads);
        tickets = new AtomicIntegerArray(maxNumberOfThreads);
        currTicket = new AtomicInteger(1);
    }

    @Override
    public void lock() {
        tickets.set(getId(), getAndChange());

        for (int i = 0; i < tickets.length(); ++i){
            if (i != getId()){
                while ( tickets.get(i) != 0 && (tickets.get(getId()) > tickets.get(i))){
                    Thread.yield();
                }
            }
        }

    }

    private int getAndChange() {
        while (true) {
            int ticket = currTicket.get();
            if (ticket == MAX) {
                if (currTicket.compareAndSet(MAX, 1)) {
                    return MAX;
                } else {
                    continue;
                }
            }
            if (currTicket.compareAndSet(ticket, ticket + 1)) {
                return ticket;
            }
        }
    }

    @Override
    public void unlock() {
        tickets.set(getId(), 0);
    }

}