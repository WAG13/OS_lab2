import task6.FixnumLock;
public class LockTestThread extends Thread {
    private FixnumLock lock;
    volatile static int value = 0;
    public LockTestThread(FixnumLock lock) {

        super();
        System.out.println("Creating new thread...");
        this.lock = lock;
    }
    @Override
    public void run() {

        while(!lock.register()) {
            Thread.yield();
        }

        lock.lock();
        System.out.println("Locked thread: " + Thread.currentThread().getName());
        for (int i = 0; i < 100000; i++) {
            f();
        }

        lock.unlock();
        System.out.println("Unlocked thread: " + Thread.currentThread().getName());

        lock.unregister();
    }

    private void f() {
        value++;
    }
}
