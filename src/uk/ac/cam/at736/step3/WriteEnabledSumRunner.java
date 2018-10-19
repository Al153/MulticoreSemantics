package uk.ac.cam.at736.step3;

import uk.ac.cam.at736.step3.arrays.SharedArray;

public class WriteEnabledSumRunner extends SumRunner {
    private int K;

    public WriteEnabledSumRunner(SharedArray arr, int iterations, int k) {
        super(arr, iterations);
        K = k;
    }

    @Override
    public void run() {
        for (int i = 0; i < iterations && !shutdown; i++) {
            if (i % K == 0) {
                this.arr.write(i, K);
            } else {
                this.temp = this.arr.sum();
            }
        }

        this.shutdown = false;

    }
}
