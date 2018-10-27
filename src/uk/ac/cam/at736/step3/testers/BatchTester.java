package uk.ac.cam.at736.step3.testers;

import uk.ac.cam.at736.step3.SumRunner;
import uk.ac.cam.at736.step3.WriteEnabledSumRunner;
import uk.ac.cam.at736.step3.arrays.SharedArray;
import uk.ac.cam.at736.step3.config.BatchTestConfig;
import uk.ac.cam.at736.step3.data.BatchTestResult;

import java.time.Duration;
import java.time.Instant;

public class BatchTester {
    private BatchTestConfig cfg;
    public BatchTester(BatchTestConfig cfg) {
        this.cfg = cfg;
    }

    public BatchTestResult runBatch() throws InterruptedException {
        SharedArray array = cfg.getBuildArray().apply(cfg.getArraySize());

        int otherThreadCount = cfg.getThreads().getCount();
        int iterationsPerInstance = cfg.getIterations().getCount();
        int batchSize = cfg.getNoBatches().getCount();

        SumRunner[] otherThreads = new SumRunner[otherThreadCount];

        for (int i = 0; i < otherThreadCount; i++) {
            otherThreads[i] = cfg.getWrite().isEnabled()
                    ? new WriteEnabledSumRunner(array, iterationsPerInstance, i+1,  cfg.getWrite().getK())
                    : new SumRunner(array, iterationsPerInstance, i+1);
        }

        SumRunner primary = new SumRunner(array, iterationsPerInstance, 0);

        long[] results = new long[batchSize];

        for (int i = 0; i < batchSize; i++) {
            results[i] = runTestInstance(primary, otherThreads);
            System.gc();
        }

        return new BatchTestResult(results);
    }

    private long runTestInstance(SumRunner primary, SumRunner[] others) throws InterruptedException {

        Thread mainThread = new Thread(primary);



        Thread[] otherThreads = new Thread[others.length];

        // construct all the threads
        for (int i = 0; i < others.length; i++) {
            otherThreads[i] = new Thread(others[i]);
        }


        Instant start = Instant.now();
        mainThread.start();

        for (Thread t: otherThreads){
            t.start();
        }

        mainThread.join();

        for (SumRunner thread : others) {
            thread.shutdown();
        }
        Instant end = Instant.now();

        return Duration.between(start, end).toNanos();
    }
}

