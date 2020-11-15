package task3;

import task6.FixnumLock;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BakeryLockTest {

    private static class Increment implements Runnable {
        private final FixnumLock lock;
        private final AtomicInteger counter;

        public Increment(FixnumLock lock, AtomicInteger counter) {
            this.lock = lock;
            this.counter = counter;
        }

        @Override
        public void run() {
            lock.register();
            lock.lock();
            counter.set(counter.intValue() + 1);
            System.out.println("[Increment]\n" +
                               "Thread id: " + Thread.currentThread().getId() + "\n" +
                               "Counter: " + counter + "\n");
            lock.unlock();
        }
    }

    private static class Decrement implements Runnable {
        private final FixnumLock lock;
        private final AtomicInteger counter;

        public Decrement(FixnumLock lock, AtomicInteger counter) {
            this.lock = lock;
            this.counter = counter;
        }

        @Override
        public void run() {
            lock.register();
            lock.lock();
            counter.set(counter.intValue() - 1);
            System.out.println("[Decrement]\n" +
                               "Thread id: " + Thread.currentThread().getId() + "\n" +
                               "Counter: " + counter + "\n");
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final int numOfOperations = 5;
        BakeryLock lock = new BakeryLock(numOfOperations * 2);
        AtomicInteger counter = new AtomicInteger(0);

        List<Thread> increments = Stream.generate(() -> new Thread(new Increment(lock, counter)))
                                        .limit(numOfOperations)
                                        .collect(Collectors.toList());
        List<Thread> decrements = Stream.generate(() -> new Thread(new Decrement(lock, counter)))
                                        .limit(numOfOperations)
                                        .collect(Collectors.toList());

        for (int i = 0; i < numOfOperations; i++) {
            increments.get(i).start();
            decrements.get(i).start();
        }
    }
}
