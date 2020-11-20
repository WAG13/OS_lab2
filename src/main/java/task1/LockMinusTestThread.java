package task1;

import task6.FixnumLock;

public class LockMinusTestThread extends Thread {
    private FixnumLock lock;

    volatile Counter counterObject;
    public LockMinusTestThread(FixnumLock lock, Counter counterObject ) {

        System.out.println("Creating new thread...");
        this.lock = lock;
        this.counterObject = counterObject;
    }

    @Override
    public void run() {

        while(!lock.register()) {
            Thread.yield();
        }

        for (int i = 0; i < 10000; i++) {
            lock.lock();

            System.out.println("Locked thread: " + Thread.currentThread().getName());

            counterObject.counter--;

            System.out.println(Thread.currentThread().getName() + " decremented counter. Current value of counter: " + counterObject.counter);
            lock.unlock();
            System.out.println("Unlocked thread: " + Thread.currentThread().getName());
        }



        lock.unregister();
    }

}
