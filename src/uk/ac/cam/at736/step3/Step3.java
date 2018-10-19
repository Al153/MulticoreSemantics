package uk.ac.cam.at736.step3;

import lombok.Value;
import uk.ac.cam.at736.step3.arrays.SharedArray;
import uk.ac.cam.at736.step3.arrays.UnsafeSharedArray;

import java.time.Duration;
import java.time.Instant;

public class Step3 {
    private SharedArray array;
    private int additionalThreadCount;
    private boolean verbose = true;

    public Step3(SharedArray a, int n) {
        array = a;
        additionalThreadCount = n;

    }

    private BatchTestResult runBatch(int batchSize, int iterationsPerInstance, int otherThreadCount, SharedArray array) throws InterruptedException {
        SumRunner[] otherThreads = new SumRunner[otherThreadCount];

        for (int i = 0; i < otherThreadCount; i++) {
            otherThreads[i] = new SumRunner(array, iterationsPerInstance);
        }

        SumRunner primary = new SumRunner(array, iterationsPerInstance);

        long[] results = new long[batchSize];

        for (int i = 0; i < batchSize; i++) {

            if (verbose) System.out.println("Starting test (threads, iterations) = " + additionalThreadCount +
                    ", " + iterationsPerInstance + ";");

            results[i] = runTestInstance(primary, otherThreads);

            if (verbose) System.out.println(
                    "Completed test (threads, iterations) = " + additionalThreadCount +
                            ", " + iterationsPerInstance + "; in " +
                            results[i] + "ns");
        }

        return new BatchTestResult(results);
    }

    private long runTestInstance(SumRunner primary, SumRunner[] otherThreads) throws InterruptedException {

        Thread mainThread = new Thread(primary);

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


        return Duration.between(start, end).toNanos();
    }



    public void run()
}

