package task2.framework;

import task1.DekkerLock;
import task2.SpinLock;
import task2.counter.AtomicCounter;
import task2.counter.BlockingCounter;
import task2.counter.Counter;
import task3.BakeryLock;
import task5.ImprovedBakeryLock;
import task6.FixnumLock;

public class Framework {

    public static void main(String[] args) {
        final int numOfIterations = 10;
        int numOfThreads = 5;
        final int operationsPerThread = 1000;
        double time = 0;

        Counter counter;
        FixnumLock lock;
        CounterBenchmark counterBenchmark;
        FixnumLockBenchmark benchmark;

        for (int i = numOfThreads; i < 21; i += 5) {
            System.out.println("Number of threads trying to access critical region simultaneously: " + i);
            System.out.println("ATOMIC");
            for (int j = 0; j < numOfIterations; j++) {
                counter = new AtomicCounter(false);
                counterBenchmark = new CounterBenchmark(counter, i, operationsPerThread, false);
                time += counterBenchmark.run();
            }
            System.out.println("Average time of increment/decrement operation: " + time / numOfIterations + "ms");
            time = 0;

            System.out.println("MONITOR");
            for (int j = 0; j < numOfIterations; j++) {
                counter = new BlockingCounter(false);
                counterBenchmark = new CounterBenchmark(counter, i, operationsPerThread, false);
                time += counterBenchmark.run();
            }
            System.out.println("Average time of increment/decrement operation: " + time / numOfIterations + "ms");
            time = 0;

            System.out.println("SPINLOCK");
            for (int j = 0; j < numOfIterations; j++) {
                lock = new SpinLock(i);
                benchmark = new FixnumLockBenchmark(lock, i, operationsPerThread, false);
                time += benchmark.run();
            }
            System.out.println("Average time of increment/decrement operation: " + time / numOfIterations + "ms");
            time = 0;

            System.out.println("DEKKER LOCK");
            for (int j = 0; j < numOfIterations; j++) {
                lock = new DekkerLock();
                benchmark = new FixnumLockBenchmark(lock, 2, 10000,
                        false);
                time += benchmark.run();
            }
            System.out.println("Average time of increment/decrement operation: " + time / numOfIterations + "ms");
            time = 0;

            System.out.println("BAKERY LOCK");
            for (int j = 0; j < numOfIterations; j++) {
                lock = new BakeryLock(i);
                benchmark = new FixnumLockBenchmark(lock, i, operationsPerThread, false);
                time += benchmark.run();
            }
            System.out.println("Average time of increment/decrement operation: " + time / numOfIterations + "ms");
            time = 0;

            System.out.println("IMPROVED BAKERY LOCK");
            for (int j = 0; j < numOfIterations; j++) {
                lock = new ImprovedBakeryLock(i);
                benchmark = new FixnumLockBenchmark(lock, i, operationsPerThread, false);
                time += benchmark.run();
            }
            System.out.println("Average time of increment/decrement operation: " + time / numOfIterations + "ms");
            time = 0;

            System.out.println("-------------------------------------------------");
        }
    }
}
