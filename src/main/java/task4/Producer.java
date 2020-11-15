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
        while (!stopped) {
            queue.put(counter++);
            if (counter == Integer.MAX_VALUE) counter = 0;
        }
    }

    public synchronized void stopProducer() {

        stopped = true;
        ItemLossQueue.check = false;

        ItemLossQueue.isReady = false;

    }
}
