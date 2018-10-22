package uk.ac.cam.at736.step3.config;

import lombok.Value;
import uk.ac.cam.at736.step3.arrays.SharedArray;
import uk.ac.cam.at736.step3.data.ThreadCount;

import java.util.function.Function;

@Value
public class BatchTestConfig {
    Function<ArraySize, SharedArray> buildArray;
    IterationsToComplete iterations;
    TestsPerBatch noBatches;
    ThreadCount threads;
    ArraySize arraySize;
    WriteEnabled write;
    boolean verbose;

}

