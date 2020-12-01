package task4;

public class Producer extends Thread {
    private Queue queue;
    private int counter = 0;

    public static boolean stopped = false;
    private String type;
    public Producer(Queue queue, String type) {
        this.queue = queue;
        this.type = type;
    }

    @Override
    public void run() {
        if (type.equals("DEADLOCK")) {
            executeTasks();
        }
        else {
            synchronized (queue) {
                executeTasks();
            }
        }


    }

    public void stopProducer() {
        synchronized (queue) {
            stopped = true;
            ItemLossQueue.stopQueue = false;

        }
    }
    private void executeTasks() {
        while (!stopped) {
            queue.put(counter++);
            if (counter == Integer.MAX_VALUE) counter = 0;
        }
    }
}
