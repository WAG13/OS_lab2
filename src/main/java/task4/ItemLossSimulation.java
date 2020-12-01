package task4;

public class ItemLossSimulation {
    public static void main(String[] args) {
        Queue queue = new ItemLossQueue();
        Producer producer = new Producer(queue, "ITEMLOSS");
        Consumer consumer = new Consumer(queue, producer::stopProducer, "ITEMLOSS");


        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException ex) {
        }
    }
}
