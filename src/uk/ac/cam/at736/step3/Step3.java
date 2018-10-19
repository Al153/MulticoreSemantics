package uk.ac.cam.at736.step3;

import lombok.Value;
import uk.ac.cam.at736.step3.arrays.*;

import java.time.Duration;
import java.time.Instant;

public class Step3 {
    private SharedArray array;

    private boolean verbose = true;

    public Step3() {


    }


    public Step3( boolean v) {
        verbose = v;

    }

    private BatchTestResult runBatch(int batchSize, int iterationsPerInstance, int otherThreadCount, SharedArray array) throws InterruptedException {
        SumRunner[] otherThreads = new SumRunner[otherThreadCount];

        for (int i = 0; i < otherThreadCount; i++) {
            otherThreads[i] = new SumRunner(array, iterationsPerInstance);
        }

        SumRunner primary = new SumRunner(array, iterationsPerInstance);

        long[] results = new long[batchSize];

        for (int i = 0; i < batchSize; i++) {

            if (verbose) System.out.println("Starting test (threads, iterations) = " + otherThreadCount +
                    ", " + iterationsPerInstance + ";");

            results[i] = runTestInstance(primary, otherThreads);

            if (verbose) System.out.println(
                    "Completed test (threads, iterations) = " + otherThreadCount +
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


    public static void main(String[] args) throws InterruptedException {
        Step3 tester = new Step3(false);
        SharedArray arr = new TTSSharedArray(5);

        System.out.println("Starting");
        for (int i = 0; i < 20; i ++) {
            BatchTestResult result = tester.runBatch(10, 100, i, arr);

            System.out.println("Threads: " + i + " " + result.prettyPrint());
        }

    }
}

