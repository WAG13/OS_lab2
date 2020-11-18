package task1;

public class WithoutLockTestThread implements Runnable {
    public static int counter = 0;

    public WithoutLockTestThread() {
        System.out.println("Creating new thread...");
    }

    @Override
    public void run() {
        System.out.println("Thread without lock: " + Thread.currentThread().getName());
        for (int i = 0; i < 100000; i++) {
            f();
        }
    }

    private void f() {
        counter++;
    }
}
