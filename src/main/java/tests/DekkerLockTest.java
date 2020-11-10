package tests;

import task1.DekkerLock;

import java.util.ArrayList;

public class DekkerLockTest {

    public static void main(String[] args) {
        test(new WithoutLockTestThread());
        test(new LockTestThread(new DekkerLock(2)));
        printResult();
    }

    private static void test(Runnable runnable) {
        int numOfThreads = 2;
        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0;  i < numOfThreads; i++) {
            threads.add(new Thread(runnable));
        }

        for(Thread thread: threads) {
            thread.start();
        }

        for(Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException x) {
                x.printStackTrace();
            }
        }


    }
    private static void printResult() {
        System.out.println("----------------------------------------------------");
        System.out.println(("Counter with Lock: " + LockTestThread.value));
        System.out.println(("Counter without Lock: " + WithoutLockTestThread.counter));
        System.out.println("----------------------------------------------------");
    }
}