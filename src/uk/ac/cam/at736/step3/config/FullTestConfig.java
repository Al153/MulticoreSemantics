package uk.ac.cam.at736.step3.config;

import lombok.Value;
import uk.ac.cam.at736.step3.data.ThreadCount;

import java.util.LinkedList;
import java.util.List;

@Value
public class FullTestConfig {

    List<TestInstance> testCases;
    List<ArraySize> sizes;

    TestsPerBatch batchNo;
    ThreadCount maxThreads;
    IterationsToComplete iterations;
    WriteEnabled enabled;
    boolean verbose;





    public List<ArraySizeProgressionConfig> getCfgs() {
        LinkedList<ArraySizeProgressionConfig> result = new LinkedList<>();
        for (TestInstance testCase : testCases) {
            result.add(
                    new ArraySizeProgressionConfig(
                            testCase.getName(),
                            sizes,
                            new ThreadCount(0),
                            maxThreads,
                            1,
                            testCase.getBuildArray(),
                            iterations,
                            batchNo,
                            enabled,
                            verbose

                    )
            );
        }

        return result;
    }
}
