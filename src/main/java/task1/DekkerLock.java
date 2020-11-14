package task1;

import task6.AbstractFixnumLock;
import java.util.ArrayList;

import static utils.Utils.getFilledList;

public class DekkerLock extends AbstractFixnumLock {
    private volatile static int turn = 0;

    private volatile ArrayList<Boolean> flag = getFilledList(getMaxNumberOfThreads(), false);

    public DekkerLock (int maxNumberOfThreads) {
        super(2);
    }

    @Override
    public void lock() {
        flag.set(getId(), true);
        while(flag.get(invertedPid())) {
            if (turn != getId()) {
                flag.set(getId(), false);
                while (turn != getId()) {
                    Thread.yield();
                }
                flag.set(getId(), true);
            }
        }
    }

    @Override
    public void unlock() {
        flag.set(getId(), false);

    }

    private int invertedPid() {
        return getId()^1;
    }
}