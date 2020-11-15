package task2.counter;

/*
 * An equivalent to monitor primitive
 */
public class BlockingCounter implements Counter {
    private int counter;
    private final boolean showCounterManipulations;

    public BlockingCounter(boolean showCounterManipulations) {
        this.counter = 0;
        this.showCounterManipulations = showCounterManipulations;
    }

    @Override
    public int getCounter() {
        return counter;
    }

    @Override
    public synchronized int increment() {
        counter++;

        if (showCounterManipulations) {
            System.out.println("[Increment]\n" +
                    "Thread id: " + Thread.currentThread().getId() + "\n" +
                    "Counter: " + counter + "\n");
        }

        return counter;
    }

    @Override
    public synchronized int decrement() {
        counter--;

        if (showCounterManipulations) {
            System.out.println("[Decrement]\n" +
                    "Thread id: " + Thread.currentThread().getId() + "\n" +
                    "Counter: " + counter + "\n");
        }

        return counter;
    }
}
