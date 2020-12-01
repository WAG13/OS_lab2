package task4;

public class Consumer extends Thread {
    private Queue queue;
    private int counter = 0;
    private boolean breakLoop = true;
    private ConsumerCallback callback;
    private String type;
    @FunctionalInterface
    public interface ConsumerCallback {
        void onItemLost();
    }


    public Consumer(Queue queue, ConsumerCallback callback, String type) {
        this.queue = queue;
        this.callback = callback;
        this.type = type;
    }

    @Override
    public void run() {
        while (breakLoop) {
            if (type.equals( "DEADLOCK")) {
                executeTasks();
            }
            else {
                synchronized (queue) {
                    executeTasks();
                }
            }


        }
    }
    private void executeTasks() {
        int item = queue.take();

        if (item != counter) {
            System.out.println("Item lost: " + counter);
            if (callback != null)
            {
                callback.onItemLost();
            }
            breakLoop=false;
        }
        else {
            counter++;
        }
    }
}
