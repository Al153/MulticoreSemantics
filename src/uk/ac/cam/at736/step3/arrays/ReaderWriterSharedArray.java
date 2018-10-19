package uk.ac.cam.at736.step3.arrays;

import java.util.concurrent.atomic.AtomicInteger;

public final class ReaderWriterSharedArray extends SharedArray {
    private AtomicInteger lock = new AtomicInteger(0);

    public ReaderWriterSharedArray(int size) {
        super(size);
    }

    private void acquireWrite() {
        do {
            if (lock.get() == 0 && lock.compareAndSet(0, -1)) break;
        } while (true);
    }

    private void releaseWrite() {
        lock.set(0);
    }

    private void acquireRead() {
        do {
            int oldVal = lock.get();
            if ((oldVal >= 0) && lock.compareAndSet(oldVal, oldVal + 1)) break;
        } while (true);
    }

    private void releaseRead() {
        int oldVal;
        do {
            oldVal = lock.get();
        } while (!lock.compareAndSet(oldVal, oldVal - 1));
    }

    @Override
    public int sum() {
        acquireRead();
        int sum = 0;
        for (int anArr : arr) {
            sum += anArr;
        }
        releaseRead();
        return sum;
    }


    @Override
    public void write(int index, int value) {
        acquireWrite();
        arr[index % arr.length] = value;
        releaseWrite();
    }

}
