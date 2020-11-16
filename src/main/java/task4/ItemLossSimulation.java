package task4;

public class ItemLossSimulation {
    public static void main(String[] args) {
        Queue queue = new DeadLockQueue();
        //Producer producer = new Producer(queue, "ITEMLOSS");
        //Consumer consumer = new Consumer(queue, producer::stopProducer, "ITEMLOSS");
        Producer producer = new Producer(queue, "DEADLOCK");
        Consumer consumer = new Consumer(queue, producer::stopProducer, "DEADLOCK");

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException ex) {
        }
    }
}
