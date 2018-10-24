package uk.ac.cam.at736.step3.config;

import lombok.Value;
import uk.ac.cam.at736.step3.data.ThreadCount;

import java.util.LinkedList;
import java.util.List;

@Value
public class FullTestConfig {

    List<TestInstance> testCases;

    TestsPerBatch batchNo;
    ThreadCount maxThreads;
    IterationsToComplete iterations;
    boolean verbose;


    public List<ArraySizeProgressionConfig> getCfgs() {
        LinkedList<ArraySizeProgressionConfig> result = new LinkedList<>();
        for (TestInstance testCase : testCases) {
            result.add(
                    new ArraySizeProgressionConfig(
                            testCase.getName(),
                            testCase.getSizes(),
                            new ThreadCount(0),
                            maxThreads,
                            1,
                            testCase.getBuildArray(),
                            iterations,
                            batchNo,
                            testCase.getEnabled(),
                            verbose
                    )
            );
        }

        return result;
    }
}
