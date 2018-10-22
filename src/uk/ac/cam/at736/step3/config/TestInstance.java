package uk.ac.cam.at736.step3.config;

import lombok.Value;
import uk.ac.cam.at736.step3.arrays.SharedArray;

import java.util.function.Function;

@Value
public class TestInstance {
    String name;
    Function<ArraySize, SharedArray> buildArray;
}
