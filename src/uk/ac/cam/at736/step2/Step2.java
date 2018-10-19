package uk.ac.cam.at736.step2;


import uk.ac.cam.at736.DelayThread;

import java.time.Duration;
import java.time.Instant;

public class Step2 {
    private final int N;
    private final int DELAY;
    private final Thread[] threads;

    public Step2(int n, int delay) {
        N = n;
        DELAY = delay;
        this.threads = new Thread[N];
        for (int i = 0; i < N; i++) {
            this.threads[i] = new Thread(new DelayThread(delay, false));
        }

    }

    public void runTest() {
        Instant start = Instant.now();

        for (int i = 0; i < N; i++) {
            this.threads[i].start();
        }
        try {
            for (int i = 0; i < N; i++) {

                this.threads[i].join();

            }
        } catch (InterruptedException e) {
            System.out.println("Caught: " + e);
        }
        Instant end = Instant.now();

        System.out.println("N: " + N +  " elapsed = " + (Duration.between(start, end).toMillis()));
    }

    public static void main(String[] args) {
        for (int n = 1; n <= 12; n ++) {
            Step2 harness = new Step2(n, 100);
            harness.runTest();
        }
    }
}
