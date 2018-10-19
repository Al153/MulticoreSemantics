package uk.ac.cam.at736.step3;


import uk.ac.cam.at736.step3.arrays.SharedArray;

public class SumRunner implements Runnable {
    private final SharedArray arr;
    private final int iterations;

    private volatile boolean shutdown = false;

    public void shutdown() {
        this.shutdown = true;
    }

    public SumRunner(SharedArray arr, int iterations) {
        this.arr = arr;
        this.iterations = iterations;
    }

    private volatile int temp = 0;

    @Override
    public void run() {
        for (int i = 0; i < iterations && !shutdown; i++) {
            this.temp = this.arr.sum();
        }

    }
}

class WriteEnabledSumRunner extends SumRunner {

    public WriteEnabledSumRunner(SharedArray arr, int iterations) {
        super(arr, iterations);
    }
}