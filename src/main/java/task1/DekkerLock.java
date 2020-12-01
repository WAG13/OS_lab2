package task1;

import task6.AbstractFixnumLock;


public class DekkerLock extends AbstractFixnumLock {
    private volatile static int turn = 0;

    private volatile boolean flag[] = {false, false};

    public DekkerLock () {
        super(2);
    }

    @Override
    public void lock() {
        flag[getId()] = true;

        while(flag[getAnotherProcessId()]) {
            if (turn != getId()) {
                flag[getId()] = false;
                while (turn != getId()) { }
                flag[getId()] = true;
            }
        }
    }

    @Override
    public void unlock() {
        flag[getId()] = false;
        turn = getAnotherProcessId();
    }

    private int getAnotherProcessId() {
        return getId()^1;
    }
}