package task1;

import java.util.ArrayList;

public class RaceConditionTest {

    public static void main(String[] args) throws InterruptedException{
        Counter counter = new Counter();
        DekkerLock locker = new DekkerLock();
        Thread thread1 = new LockPlusTestThread(locker, counter);
        Thread thread2 = new LockMinusTestThread(locker, counter);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        printResult(counter);
    }


    private static void printResult(Counter counterObject) {
        System.out.println("----------------------------------------------------");
        System.out.println(("Counter with Lock: " + counterObject.counter));
        System.out.println("----------------------------------------------------");
    }
}
