package uk.ac.cam.at736.step3.arrays;

import java.util.concurrent.atomic.AtomicBoolean;

public final class TTSSharedArray extends SharedArray {
    private AtomicBoolean lock = new AtomicBoolean(false);

    protected TTSSharedArray(int x) {
        super(x);
    }

    private void waitLock() {
        do {

            while (lock.get()) {
            }
        } while (lock.compareAndSet(false, true));
    }

    private void unlock() {
        this.lock.set(false);
    }


    @Override
    public void write(int index, int value) {
        waitLock();
        arr[index] = value;
        unlock();
    }


    @Override
    public int sum() {
        waitLock();
        int sum = 0;
        for (int anArr : arr) {
            sum += anArr;
        }
        unlock();
        return sum;
    }



}
