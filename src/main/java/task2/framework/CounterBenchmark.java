package task2.framework;

import task2.counter.Counter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CounterBenchmark {
    private final Counter counter;
    private final int numOfThreads;
    private final int operationsPerThread;
    private final boolean showCounterManipulations;


    private static class Increment implements Callable<Double> {
        private final Counter counter;
        private final int numOfOperations;

        public Increment(Counter counter, int numOfOperations) {
            this.counter = counter;
            this.numOfOperations = numOfOperations;
        }

        @Override
        public Double call() {
            long elapsedTime = 0L;
            long start;

            start = System.currentTimeMillis();
            for (int i = 0; i < numOfOperations; i++) {
                counter.increment();
            }
            elapsedTime += System.currentTimeMillis() - start;

            return (double) elapsedTime / numOfOperations;
        }
    }


    private static class Decrement implements Callable<Double> {
        private final Counter counter;
        private final int numOfOperations;

        public Decrement(Counter counter, int numOfOperations) {
            this.counter = counter;
            this.numOfOperations = numOfOperations;
        }

        @Override
        public Double call() {
            long elapsedTime = 0L;
            long start;

            start = System.currentTimeMillis();
            for (int i = 0; i < numOfOperations; i++) {
                counter.decrement();
            }
            elapsedTime += System.currentTimeMillis() - start;

            return (double) elapsedTime / numOfOperations;
        }
    }


    public CounterBenchmark(Counter counter, int numOfThreads, int operationsPerThread,
                            boolean showCounterManipulations) {
        this.counter = counter;
        this.numOfThreads = numOfThreads;
        this.operationsPerThread = operationsPerThread;
        this.showCounterManipulations = showCounterManipulations;
    }

    public double run() {
        ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
        List<Future<Double>> futures = new ArrayList<>(numOfThreads);
        double elapsedTime = 0;

        if (showCounterManipulations) {
            System.out.println("Initial counter: 0\n");
        }

        for (int i = 0; i < numOfThreads / 2; i++) {
            futures.add(executorService.submit(new CounterBenchmark.Increment(counter, operationsPerThread)));
        }
        for (int i = 0; i < numOfThreads - numOfThreads / 2; i++) {
            futures.add(executorService.submit(new CounterBenchmark.Decrement(counter, operationsPerThread)));
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
