package uk.ac.cam.at736.step3.config;

import lombok.Value;
import uk.ac.cam.at736.step3.arrays.SharedArray;
import uk.ac.cam.at736.step3.data.ThreadCount;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

@Value
public class ArraySizeProgressionConfig {
    String testName;

    ArraySize minSize;
    ArraySize maxSize;
    int arraySizeStep;

    ThreadCount minThreads;
    ThreadCount maxThreads;
    int threadCountStep;

    Function<ArraySize, SharedArray> buildArray;
    IterationsToComplete iterations;
    TestsPerBatch noBatches;
    WriteEnabled enabled;

    boolean verbose;

    public List<ThreadProgressionConfig> getThreadProgressionConfigs() {
        LinkedList<ThreadProgressionConfig> cfgs = new LinkedList<>();

        for (int i = minSize.getSize(); i < maxSize.getSize(); i += arraySizeStep) {
            cfgs.add(
                    new ThreadProgressionConfig(
                            minThreads,
                            maxThreads,
                            threadCountStep,
                            buildArray,
                            iterations,
                            noBatches,
                            new ArraySize(i),
                            enabled,
                            verbose
                    )
            );
        }

        return cfgs;
    }

}
