package task6;

import java.util.concurrent.locks.Lock;

public interface FixnumLock extends Lock {

    int getId();
    void register();
    void unregister();

    int getMaxNumberOfThreads();
}
