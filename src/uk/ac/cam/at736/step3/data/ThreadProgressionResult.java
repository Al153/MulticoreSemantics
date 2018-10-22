package uk.ac.cam.at736.step3.data;

import lombok.Value;

import java.util.Map;

@Value
public class ThreadProgressionResult {
    Map<ThreadCount, BatchTestResult> results;
}
