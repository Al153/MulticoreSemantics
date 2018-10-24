package uk.ac.cam.at736.step3.testers;

import lombok.AllArgsConstructor;
import uk.ac.cam.at736.step3.config.ArraySizeProgressionConfig;
import uk.ac.cam.at736.step3.config.ThreadProgressionConfig;
import uk.ac.cam.at736.step3.config.ArraySize;
import uk.ac.cam.at736.step3.data.ArraySizeProgressionResult;
import uk.ac.cam.at736.step3.data.ThreadProgressionResult;

import java.util.HashMap;

@AllArgsConstructor
public class ArraySizeProgressionTester {

    private ArraySizeProgressionConfig cfg;

    public ArraySizeProgressionResult doTest() throws InterruptedException {
        HashMap<ArraySize, ThreadProgressionResult> results = new HashMap<>();

        for (ThreadProgressionConfig threadCfg : cfg.getThreadProgressionConfigs()) {

            if (cfg.isVerbose()){
                System.out.println("\t\tStarting ArraySize: " + threadCfg.getSize().getSize());
            }


            ThreadProgressionTester tester = new ThreadProgressionTester(threadCfg);
            results.put(threadCfg.getSize(), tester.doProgressionTest());

            if (cfg.isVerbose()){
                System.out.println("\t\tEnding ArraySize: " + threadCfg.getSize().getSize());
            }

        }

        return new ArraySizeProgressionResult(cfg.getTestName(), results);
    }
}

