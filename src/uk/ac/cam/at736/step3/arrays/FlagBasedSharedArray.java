package uk.ac.cam.at736.step3.arrays;

import java.util.concurrent.atomic.AtomicInteger;

public class FlagBasedSharedArray extends SharedArray {
    private final boolean[] flags;
    private final AtomicInteger owner;

    public FlagBasedSharedArray(int size, int MAX_THREADS) {
        super(size);
        owner = new AtomicInteger(0);
        flags = new boolean[MAX_THREADS];
    }

    private void acquireRead(int threadNo) {
        while (true){
            flags[threadNo] = true;
            if(owner.get() == 0) return;
            flags[threadNo] = false;
        }
    }

    private void releaseRead(int threadNo) {
        flags[threadNo] = false;
    }

    private void acquireWrite(int threadNo) {
        while (true) {
            if (owner.compareAndSet(0, threadNo+1)){
                for (int i = 0; i < flags.length; i ++){
                    while (flags[i]){}
                }
                return;
            }
        }
    }

    private void releaseWrite(int threadNo) {
        owner.set(0);
    }



    @Override
    public int sum(int threadNo) {
        acquireRead(threadNo);
        int total = 0;
        for (int i = 0; i < arr.length; i++) {
            total += i;
        }

        releaseRead(threadNo);
        return total;
    }

    @Override
    public void write(int index, int value, int threadNo) {
        acquireWrite(threadNo);
        arr[index % arr.length] = value;
        releaseWrite(threadNo);
    }
}
