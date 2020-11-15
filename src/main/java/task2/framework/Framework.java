package task2.framework;

import task3.BakeryLock;
import task6.FixnumLock;

public class Framework {

    public static void main(String[] args) {
        final int numOfThreads = 10;
        final int operationsPerThread = 50;
        FixnumLock lock = new BakeryLock(numOfThreads);
        FixnumLockBenchmark benchmark = new FixnumLockBenchmark(lock, numOfThreads, operationsPerThread,
                                                                false);
        double time = benchmark.run();
        System.out.print(time);
    }
}
