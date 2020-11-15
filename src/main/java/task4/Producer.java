package task4;

public class Producer extends Thread {
    private Queue queue;
    private int counter = 0;

    public static boolean stopped = false;

    public Producer(Queue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        synchronized (queue) {
            while (!stopped) {
                queue.put(counter++);
                if (counter == Integer.MAX_VALUE) counter = 0;
            }
        }

    }

    public void stopProducer() {
        synchronized (queue) {
            stopped = true;
            ItemLossQueue.check = false;

        }
    }
}
