package task2;

/*
 * An equivalent to monitor primitive
 */
public class Counter {
    private int counter = 0;

    public int getCounter() {
        return counter;
    }

    public synchronized int incrementAndGet() {
        counter++;
        return counter;
    }

    public synchronized int decrementAndGet() {
        counter--;
        return counter;
    }
}
