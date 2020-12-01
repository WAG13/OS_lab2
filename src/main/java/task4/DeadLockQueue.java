package task4;

public class DeadLockQueue implements Queue {
    private int item = ItemLossQueue.EMPTY_QUEUE;

    @Override
    public void put(int item) {
        System.out.println("Produced item " + item);

        while (this.item != ItemLossQueue.EMPTY_QUEUE) {
            synchronized (this) {
                try {
                    System.out.println("Producer waiting");
                    wait();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        this.item = item;

        synchronized (this) {
            try {
                notifyAll();
            } catch (Exception ex) {
            }
        }
    }

    @Override
    public int take() {
        while (item == ItemLossQueue.EMPTY_QUEUE) {
            synchronized (this) {
                try {
                    System.out.println("Consumer waiting");
                    wait();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        System.out.println("Consumed item " + item);

        int res = item;
        item = ItemLossQueue.EMPTY_QUEUE;
        synchronized (this) {
            try {
                notifyAll();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return res;
    }
}
