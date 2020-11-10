package task1;
import task6.AbstractFixnumLock;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public class DekkerLock extends AbstractFixnumLock {
    private volatile static  int turn = 0;

    private volatile ArrayList<Boolean> flag = getFilledList(getMaxNumberOfThreads(), false);
    public DekkerLock () {

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
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        flag.set(getId(), false);
        turn = invertedPid();
    }

    @Override
    public Condition newCondition() {
        return null;
    }
    private int invertedPid() {
        return getId()^1;
    }
}
