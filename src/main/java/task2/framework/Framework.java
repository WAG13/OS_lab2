package task2.framework;

import task1.DekkerLock;
import task2.SpinLock;
import task3.BakeryLock;
import task5.ImprovedBakeryLock;
import task6.FixnumLock;

public class Framework {

    public static void main(String[] args) {
        final int numOfThreads = 10;
        final int operationsPerThread = 50;

        System.out.println("SPINLOCK\n");
        FixnumLock lock = new SpinLock(numOfThreads);
        FixnumLockBenchmark benchmark = new FixnumLockBenchmark(lock, numOfThreads, operationsPerThread,
                                                                false);
        double time = benchmark.run();
        System.out.println("Average time of increment/decrement operation: " + time + "\n\n");

        System.out.println("DEKKER LOCK\n");
        lock = new DekkerLock();
        benchmark = new FixnumLockBenchmark(lock, 2, 10000, false);
        time = benchmark.run();
        System.out.println("Average time of increment/decrement operation: " + time + "\n\n");

        System.out.println("BAKERY LOCK\n");
        lock = new BakeryLock(numOfThreads);
        benchmark = new FixnumLockBenchmark(lock, numOfThreads, operationsPerThread, false);
        time = benchmark.run();
        System.out.println("Average time of increment/decrement operation: " + time + "\n\n");

        System.out.println("IMPROVED BAKERY LOCK\n");
        lock = new ImprovedBakeryLock(numOfThreads);
        benchmark = new FixnumLockBenchmark(lock, numOfThreads, operationsPerThread, false);
        time = benchmark.run();
        System.out.println("Average time of increment/decrement operation: " + time + "\n\n");
    }
}
