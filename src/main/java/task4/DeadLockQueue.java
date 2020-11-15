package task4;

public class DeadLockQueue implements Queue {
    private int item = -1;

    @Override
    public void put(int item) {
        System.out.println("Putting item " + item);

        while (this.item != -1) {
            synchronized (this) {
                try {
                    System.out.println("Producer waiting");
                    wait();
                } catch (Exception ex) {
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
        while (item == -1) {
            synchronized (this) {
                try {
                    System.out.println("Consumer waiting");
                    wait();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        System.out.println("Getting item " + item);

        int res = item;
        item = -1;
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
