package task1;

import java.util.ArrayList;

public class DekkerLockTest {

    public static void main(String[] args) throws InterruptedException{
        Counter counterObject = new Counter();
        test(new LockPlusTestThread(new DekkerLock(),counterObject));
        Thread.sleep(1000);
        test(new WithoutLockTestThread());

        printResult(counterObject);
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
    private static void printResult(Counter counterObject) {
        System.out.println("----------------------------------------------------");
        System.out.println(("Counter with Lock: " + counterObject.counter));
        System.out.println(("Counter without Lock: " + WithoutLockTestThread.counter));
        System.out.println("----------------------------------------------------");
    }
}