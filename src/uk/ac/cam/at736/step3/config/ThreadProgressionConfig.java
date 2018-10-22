package uk.ac.cam.at736.step3.config;

import lombok.Value;
import uk.ac.cam.at736.step3.arrays.SharedArray;
import uk.ac.cam.at736.step3.data.ThreadCount;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

@Value
public class ThreadProgressionConfig {
    ThreadCount minThreads;
    ThreadCount maxThreads;
    int threadCountStep;
    Function<ArraySize, SharedArray> buildArray;
    IterationsToComplete iterations;
    TestsPerBatch noBatches;
    ArraySize size;
    WriteEnabled writeEnabled;
    boolean verbose;



    public List<BatchTestConfig> getBatchConfigs() {
        LinkedList<BatchTestConfig> cfgs = new LinkedList<>();

        for (int i = minThreads.getCount(); i < maxThreads.getCount(); i += threadCountStep) {
            cfgs.add(
                    new BatchTestConfig(
                            buildArray,
                            iterations,
                            noBatches,
                            new ThreadCount(i),
                            size,
                            writeEnabled,
                            verbose
                    )
            );
        }

        return cfgs;
    }
}
