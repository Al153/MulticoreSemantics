package uk.ac.cam.at736.step3.data;

import lombok.Value;
import uk.ac.cam.at736.step3.config.ArraySize;

import java.util.Map;


@Value
public class ArraySizeProgressionResult {
    String testName;
    Map<ArraySize, ThreadProgressionResult> results;
}

