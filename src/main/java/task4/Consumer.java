package task4;

public class Consumer extends Thread {
    private Queue queue;
    private int counter = 0;

    private ConsumerCallback callback;

    @FunctionalInterface
    public interface ConsumerCallback {
        void onItemLost();
    }

    public Consumer(Queue queue) {
        this.queue = queue;
    }

    public Consumer(Queue queue, ConsumerCallback callback) {
        this.queue = queue;
        this.callback = callback;
    }

    @Override
    public void run() {
        while (true) {
            int item = queue.take();

            if (item != counter) {
                System.out.println("Item lost: " + counter);
                if (callback != null) callback.onItemLost();
                break;
            }
            else {
                counter++;
            }
        }
    }
}
