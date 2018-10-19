package uk.ac.cam.at736.step3;


import uk.ac.cam.at736.step3.arrays.SharedArray;

public class SumRunner implements Runnable {
    protected final SharedArray arr;
    protected final int iterations;
    protected volatile int temp = 0;
    protected volatile boolean shutdown = false;

    public SumRunner(SharedArray arr, int iterations) {
        this.arr = arr;
        this.iterations = iterations;
    }

    public void shutdown() {
        this.shutdown = true;
    }

    @Override
    public void run() {
        for (int i = 0; i < iterations && !shutdown; i++) {
            this.temp = this.arr.sum();
        }
        this.shutdown = false;
    }
}

