package task5;

import task6.AbstractFixnumLock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;

public class ImprovedBakeryLock extends AbstractFixnumLock {

    private static AtomicInteger currTicket;
    private final int MAX;
    private final AtomicIntegerArray tickets;

    private AtomicReference<Thread> lastThread;

    public ImprovedBakeryLock(int maxNumberOfThreads) {
        super(maxNumberOfThreads);
        tickets = new AtomicIntegerArray(maxNumberOfThreads);
        currTicket = new AtomicInteger(1);
        lastThread = new AtomicReference<>(null);
        MAX = maxNumberOfThreads;
    }

    @Override
    public void lock() {
        tickets.set(getId(), getAndChange());
        //System.out.println("IN ID: " + getId() + ", Ticket: " + tickets.get(getId()) + "\n\n");

        for (int i = 0; i < tickets.length(); ++i){
            if (i != getId()){
                while ( tickets.get(i) != 0 && (tickets.get(getId()) > tickets.get(i) ||
                        (tickets.get(getId()) == tickets.get(i) && getId() > i))){
                    Thread.yield();
                }
            }
        }
        //System.out.println("OUT ID: " + getId() + ", Ticket: " + tickets.get(getId()) + "\n\n");
    }

    private int getAndChange() {
        while (true) {
            int ticket = currTicket.get();
            if (ticket == MAX) {
                if (currTicket.compareAndSet(MAX, 1)) {
                    lastThread.set(Thread.currentThread());
                    return MAX;
                } else {
                    continue;
                }
            }
            if (currTicket.compareAndSet(ticket, ticket + 1)) {
                while (lastThread.get() != null) {
                    Thread.yield();
                }
                return ticket;
            }
        }
    }

    @Override
    public void unlock() {
        tickets.set(getId(), 0);
        if (Thread.currentThread() == lastThread.get()) {
            lastThread.set(null);
        }
    }

}