package uk.ac.cam.at736.step3.data;

import lombok.Value;
import uk.ac.cam.at736.step3.data.ArraySizeProgressionResult;

import java.util.Map;

@Value
public class FullTestResult {
    Map<String, ArraySizeProgressionResult> results;
}
