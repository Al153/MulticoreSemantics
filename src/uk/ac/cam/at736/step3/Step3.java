package uk.ac.cam.at736.step3;

import uk.ac.cam.at736.step3.arrays.SharedArray;
import uk.ac.cam.at736.step3.arrays.UnsafeSharedArray;

import java.time.Duration;
import java.time.Instant;

public class Step3 {
    private SharedArray array;
    private int additionalThreadCount;

    public Step3(SharedArray a, int n) {
        array = a;
        additionalThreadCount = n;

    }

    public void runTest(int iterations) throws InterruptedException {

        Thread mainThread = new Thread(new SumRunner(array, iterations));
        SumRunner[] otherThreads = new SumRunner[additionalThreadCount];

        for (int i = 0; i < additionalThreadCount; i++) {
            otherThreads[i] = new SumRunner(array, Integer.MAX_VALUE);
        }



        System.out.println("Starting test (threads, iterations) = " + additionalThreadCount +
                ", " + iterations + ";");
        Instant start = Instant.now();
        mainThread.start();
        for (SumRunner thread : otherThreads) {
            new Thread(thread).start();
        }

        mainThread.join();

        for (SumRunner thread : otherThreads) {
            thread.shutdown();
        }
        Instant end = Instant.now();

        System.out.println(
                "Completed test (threads, iterations) = " + additionalThreadCount +
                        ", " + iterations + "; in " +
                        Duration.between(start, end).toNanos() + "ns");
    }

    public static void main(String[] args) throws InterruptedException {
        SharedArray unsafe = new UnsafeSharedArray(5000);
        Step3 tester1 = new Step3(unsafe, 100);
        tester1.runTest(1000);


    }
}


