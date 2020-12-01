package task2.counter;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter implements Counter {
    private final AtomicInteger counter;
    private final boolean showCounterManipulations;

    public AtomicCounter(boolean showCounterManipulations) {
        this.counter = new AtomicInteger(0);
        this.showCounterManipulations = showCounterManipulations;
    }

    @Override
    public int getCounter() {
        return counter.get();
    }

    @Override
    public int increment() {
        int current = counter.incrementAndGet();

        if (showCounterManipulations) {
            System.out.println("[Increment]\n" +
                               "Thread id: " + Thread.currentThread().getId() + "\n" +
                               "Counter: " + current + "\n");
        }

        return current;
    }

    @Override
    public int decrement() {
        int current = counter.decrementAndGet();

        if (showCounterManipulations) {
            System.out.println("[Decrement]\n" +
                               "Thread id: " + Thread.currentThread().getId() + "\n" +
                               "Counter: " + current + "\n");
        }

        return current;
    }
}
