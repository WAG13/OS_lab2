package task4;

public class ItemLossQueue implements Queue {
    private int item = -1;
    protected static int EMPTY_QUEUE = -1;
    public static boolean check = true;
    public static boolean isReady = false;
    @Override
    public  void put(int item){

//
//        while (this.item != EMPTY_QUEUE && check) {
//            try {
//                wait();
//            }
//            catch (Exception ex) {
//
//            }
//        }
        while (this.item != EMPTY_QUEUE) {
            if (!check) {
                break;
            }
            try {
                isReady = true;
                wait();

            }
            catch (Exception ex) {

            }


        }
        isReady = false;
        if (check) {
            if (Math.random() > 0.2 ){
                this.item = item;

                System.out.println("Produced: " + item);



            }
            else {

                System.out.println("Lost item: " + item);


                //  Producer.stopped = true;
            }
            notify();
        }


    }

    public  int take(){

//        while (isReady) {
//            try {
//                wait();
//            }
//            catch (Exception ex) {
//
//            }
//        }
        while (item == EMPTY_QUEUE) {
            if (!check) {
                break;
            }
            try {

                wait();
            }
            catch (Exception ex) {

            }

        }

        if (check) {
            int taken = this.item;
            this.item = EMPTY_QUEUE;


            System.out.println("Consumed: " + taken);
            notifyAll();
            return taken;
        }



        return -1;
    }

//    protected  void notificate(String action, int item){
//
//
//
//    }
}
