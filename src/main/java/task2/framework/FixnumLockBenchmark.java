package task2.framework;

import task6.FixnumLock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FixnumLockBenchmark {
    private final FixnumLock lock;
    private final int numOfThreads;
    private final int operationsPerThread;
    private final boolean showCounterManipulations;


    private static class Increment implements Callable<Double> {
        private final FixnumLock lock;
        private final AtomicInteger counter;
        private final int numOfOperations;
        private final boolean showCounterManipulations;

        public Increment(FixnumLock lock, AtomicInteger counter, int numOfOperations,
                         boolean showCounterManipulations) {
            this.lock = lock;
            this.counter = counter;
            this.numOfOperations = numOfOperations;
            this.showCounterManipulations = showCounterManipulations;
        }

        @Override
        public Double call() {
            long elapsedTime = 0L;
            long start;
            lock.register();

            start = System.currentTimeMillis();
            for (int i = 0; i < numOfOperations; i++) {
                lock.lock();

                counter.set(counter.intValue() + 1);
                if (showCounterManipulations) {
                    System.out.println("[Increment]\n" +
                                       "Thread id: " + Thread.currentThread().getId() + "\n" +
                                       "Counter: " + counter + "\n");
                }

                lock.unlock();
            }
            elapsedTime += System.currentTimeMillis() - start;

            return (double) elapsedTime / numOfOperations;
        }
    }


    private static class Decrement implements Callable<Double> {
        private final FixnumLock lock;
        private final AtomicInteger counter;
        private final int numOfOperations;
        private final boolean showCounterManipulations;

        public Decrement(FixnumLock lock, AtomicInteger counter, int numOfOperations,
                         boolean showCounterManipulations) {
            this.lock = lock;
            this.counter = counter;
            this.numOfOperations = numOfOperations;
            this.showCounterManipulations = showCounterManipulations;
        }

        @Override
        public Double call() {
            long elapsedTime = 0L;
            long start;
            lock.register();

            start = System.currentTimeMillis();
            for (int i = 0; i < numOfOperations; i++) {
                lock.lock();

                counter.set(counter.intValue() - 1);
                if (showCounterManipulations) {
                    System.out.println("[Decrement]\n" +
                                       "Thread id: " + Thread.currentThread().getId() + "\n" +
                                       "Counter: " + counter + "\n");
                }

                lock.unlock();
            }
            elapsedTime += System.currentTimeMillis() - start;

            return (double) elapsedTime / numOfOperations;
        }
    }


    public FixnumLockBenchmark(FixnumLock lock, int numOfThreads, int operationsPerThread,
                               boolean showCounterManipulations) {
        this.lock = lock;
        this.numOfThreads = numOfThreads;
        this.operationsPerThread = operationsPerThread;
        this.showCounterManipulations = showCounterManipulations;
    }

    public double run() {
        AtomicInteger counter = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
        List<Future<Double>> futures = new ArrayList<>(numOfThreads);
        double elapsedTime = 0;

        if (showCounterManipulations) {
            System.out.println("Initial counter: 0\n");
        }

        for (int i = 0; i < numOfThreads / 2; i++) {
            futures.add(executorService.submit(
                        new Increment(lock, counter, operationsPerThread, showCounterManipulations)));
        }
        for (int i = 0; i < numOfThreads - numOfThreads / 2; i++) {
            futures.add(executorService.submit(
                    new Decrement(lock, counter, operationsPerThread, showCounterManipulations)));
        }
        executorService.shutdown();

        for (int i = 0; i < numOfThreads; i++) {
            try {
                elapsedTime += futures.get(i).get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        return elapsedTime / numOfThreads;
    }
}
