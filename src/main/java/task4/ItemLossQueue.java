package task4;

public class ItemLossQueue implements Queue {
    private int item = -1;
    public static int EMPTY_QUEUE = -1;

    public static boolean stopQueue = true;

    @Override
    public  void put(int item){

        while (this.item != EMPTY_QUEUE) {
            if (!stopQueue) {
                break;
            }
            try {
                wait();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (stopQueue) {
            if (Math.random() > 0.2 ){
                this.item = item;

                System.out.println("Produced: " + item);

            }
            else {
                System.out.println("Lost item: " + item);
            }
            notify();
        }


    }

    public int take(){

        while (item == EMPTY_QUEUE) {
            if (!stopQueue) {
                break;
            }
            try {
                wait();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (stopQueue) {
            int taken = this.item;
            this.item = EMPTY_QUEUE;
            System.out.println("Consumed: " + taken);
            notifyAll();
            return taken;
        }

        return -1;
    }

}
