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

            if (cfg.isVerbose()) System.out.println("Starting test (threads, iterations) = " + otherThreadCount +
                    ", " + iterationsPerInstance + ";");

            results[i] = runTestInstance(primary, otherThreads);

            if (cfg.isVerbose()) System.out.println(
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
}

