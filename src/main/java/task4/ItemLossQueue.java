package task4;

public class ItemLossQueue implements Queue {
    private int item = -1;
    protected static int EMPTY_QUEUE = -1;
    public static boolean check = true;
    public static boolean isReady = true;
    @Override
    public synchronized void put(int item){


        while (this.item != EMPTY_QUEUE && check) {
            try {
                wait();
            }
            catch (Exception ex) {

            }
        }
        if (Math.random() > 0.2 ){
            this.item = item;
            notificate("Produced: ", item);
            isReady = false;
        }
        else {
            notificate("Lost item: ", item);
            Producer.stopped = true;
        }

    }

    public synchronized int take(){

        while (isReady) {
            try {
                wait();
            }
            catch (Exception ex) {

            }
        }


        int taken = this.item;
        this.item = EMPTY_QUEUE;

        notificate("Consumed: ", taken);
        isReady = true;
        return taken;
    }

    protected synchronized void notificate(String action, int item){
        System.out.println(action + item);
        isReady = false;
        notify();

    }
}
