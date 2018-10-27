package uk.ac.cam.at736.step3.testers;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import uk.ac.cam.at736.step3.arrays.*;
import uk.ac.cam.at736.step3.config.*;
import uk.ac.cam.at736.step3.data.ArraySizeProgressionResult;
import uk.ac.cam.at736.step3.data.FullTestResult;
import uk.ac.cam.at736.step3.data.ThreadCount;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;

public class FullTest {
    private FullTestConfig cfg;

    public FullTest(FullTestConfig cfg) {
        this.cfg = cfg;
    }

    public FullTestResult doTest() throws InterruptedException {
        Map<String, ArraySizeProgressionResult> results = new HashMap<>();


        for (ArraySizeProgressionConfig arraySizeProgressionConfig : cfg.getCfgs()) {

            if (cfg.isVerbose()){
                System.out.println("Starting Case: " + arraySizeProgressionConfig.getTestName());
            }
            results.put(
                    arraySizeProgressionConfig.getTestName(),
                    new ArraySizeProgressionTester(arraySizeProgressionConfig).doTest()
            );


            if (cfg.isVerbose()){
                System.out.println("Ending Case: " + arraySizeProgressionConfig.getTestName());
            }
        }

        return new FullTestResult(results);
    }
}

