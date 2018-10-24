package uk.ac.cam.at736.step3.testers;

import uk.ac.cam.at736.step3.config.BatchTestConfig;
import uk.ac.cam.at736.step3.config.ThreadProgressionConfig;
import uk.ac.cam.at736.step3.data.BatchTestResult;
import uk.ac.cam.at736.step3.data.ThreadCount;
import uk.ac.cam.at736.step3.data.ThreadProgressionResult;
import uk.ac.cam.at736.step3.testers.BatchTester;

import java.util.HashMap;

public class ThreadProgressionTester {
    private ThreadProgressionConfig cfg;

    public ThreadProgressionTester(ThreadProgressionConfig cfg) {
        this.cfg = cfg;
    }

    public ThreadProgressionResult doProgressionTest() throws InterruptedException {
        HashMap<ThreadCount, BatchTestResult> results = new HashMap<>();
        for (BatchTestConfig batchCfg: cfg.getBatchConfigs()){
            if (cfg.isVerbose()){
                System.out.println("\t\t\t\tStarting ThreadCount: " + batchCfg.getThreads().getCount());
            }
            BatchTester tester = new BatchTester(batchCfg);
            results.put(batchCfg.getThreads(), tester.runBatch());
        }

        return new ThreadProgressionResult(results);
    }
}

