package uk.ac.cam.at736.step3.testers;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import uk.ac.cam.at736.step3.arrays.*;
import uk.ac.cam.at736.step3.config.*;
import uk.ac.cam.at736.step3.data.ArraySizeProgressionResult;
import uk.ac.cam.at736.step3.data.FullTestResult;
import uk.ac.cam.at736.step3.data.ThreadCount;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class  FullTest {
    private FullTestConfig cfg;

    public FullTest(FullTestConfig cfg) {
        this.cfg = cfg;
    }

    public FullTestResult doTest() throws InterruptedException {
        Map<String, ArraySizeProgressionResult> results = new HashMap<>();

        for (ArraySizeProgressionConfig arraySizeProgressionConfig: cfg.getCfgs()){
            results.put(
                    arraySizeProgressionConfig.getTestName(),
                    new ArraySizeProgressionTester(arraySizeProgressionConfig).doTest()
            );
        }

        return new FullTestResult(results);
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        List<TestInstance> cases = new LinkedList<TestInstance>();

        cases.add(new TestInstance("Unsafe", size -> new UnsafeSharedArray(size.getSize())));
        cases.add(new TestInstance("Safe", size -> new LockedSharedArray(size.getSize())));
        cases.add(new TestInstance("TATAS", size -> new TTSSharedArray(size.getSize())));
        cases.add(new TestInstance("ReaderWriter", size -> new ReaderWriterSharedArray(size.getSize())));
        cases.add(new TestInstance("Flags", size -> new FlagBasedSharedArray(size.getSize(), 20)));


        FullTestConfig cfg = new FullTestConfig(
                cases,
                new ArraySize(500),
                new ArraySize(5001), //new ArraySize(5000),
                500,
                new TestsPerBatch(100),
                new ThreadCount(16),
                new IterationsToComplete(10000),
                new WriteEnabled(false, 0),
                true
        );


        FullTest tester = new FullTest(cfg);

        FullTestResult result = tester.doTest();

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

        String HOME = System.getProperty("user.home");
        String timestamp = Instant.now().toString().replace(":", "-").split("-\\d\\d\\.")[0];
        String runName = "run_" + timestamp;
        Path p = Paths.get(HOME, "IdeaProjects", "MulticoreSemantics", "results", "data", runName);
        writer.writeValue(p.toFile(), result);
    }
}

