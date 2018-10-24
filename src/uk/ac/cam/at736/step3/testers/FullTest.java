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

    public static void main(String[] args) throws InterruptedException, IOException {
        List<TestInstance> cases = new LinkedList<TestInstance>();

        WriteEnabled readOnly = new WriteEnabled(false, 0);

        WriteEnabled write10k = new WriteEnabled(true, 10000);
        WriteEnabled write100 = new WriteEnabled(true, 100);

        List<ArraySize> standardArraySize = Collections.singletonList(new ArraySize(5000));

        cases.add(
                new TestInstance(
                        "Unsafe",
                        size -> new UnsafeSharedArray(size.getSize()),
                        Arrays.asList(new ArraySize(5), new ArraySize(1000), new ArraySize(5000)),
                        readOnly
                )
        );
        cases.add(
                new TestInstance(
                        "Safe",
                        size -> new LockedSharedArray(size.getSize()),
                        standardArraySize,
                        readOnly
                )
        );
        cases.add(
                new TestInstance(
                        "TATAS",
                        size -> new TTSSharedArray(size.getSize()),
                        standardArraySize,
                        readOnly
                )
        );
        cases.add(
                new TestInstance(
                        "ReaderWriter",
                        size -> new ReaderWriterSharedArray(size.getSize()),
                        standardArraySize,
                        readOnly
                )
        );
        cases.add(
                new TestInstance(
                        "Flags",
                        size -> new FlagBasedSharedArray(size.getSize(), 20),
                        standardArraySize,
                        readOnly
                )
        );

        cases.add(
                new TestInstance(
                        "Write_Unsafe_10k",
                        size -> new UnsafeSharedArray(size.getSize()),
                        standardArraySize,
                        write10k
                )
        );



        cases.add(
                new TestInstance(
                        "Write_Unsafe_100",
                        size -> new UnsafeSharedArray(size.getSize()),
                        standardArraySize,
                        write100
                )
        );

        cases.add(
                new TestInstance(
                        "Write_safe_10k",
                        size -> new LockedSharedArray(size.getSize()),
                        standardArraySize,
                        write10k
                )
        );


        cases.add(
                new TestInstance(
                        "Write_Safe",
                        size -> new LockedSharedArray(size.getSize()),
                        standardArraySize,
                        write100
                )
        );


        cases.add(
                new TestInstance(
                        "Write_TATAS_10k",
                        size -> new TTSSharedArray(size.getSize()),
                        standardArraySize,
                        write10k
                )
        );

        cases.add(
                new TestInstance(
                        "Write_TATAS_100",
                        size -> new TTSSharedArray(size.getSize()),
                        standardArraySize,
                        write100
                )
        );


        cases.add(
                new TestInstance(
                        "Write_RW_10k",
                        size -> new ReaderWriterSharedArray(size.getSize()),
                        standardArraySize,
                        write10k
                )
        );

        cases.add(
                new TestInstance(
                        "Write_RW_100",
                        size -> new ReaderWriterSharedArray(size.getSize()),
                        standardArraySize,
                        write100
                )
        );

        cases.add(
                new TestInstance(
                        "Write_Flags_10k",
                        size -> new UnsafeSharedArray(size.getSize()),
                        standardArraySize,
                        write10k
                )
        );

        cases.add(
                new TestInstance(
                        "Write_Flags_100",
                        size -> new UnsafeSharedArray(size.getSize()),
                        standardArraySize,
                        write100
                )
        );








        List<ArraySize> sizes = Arrays.asList(new ArraySize(5), new ArraySize(1000), new ArraySize(5000));

        FullTestConfig cfg = new FullTestConfig(
                cases,
                new TestsPerBatch(100),
                new ThreadCount(16),
                new IterationsToComplete(50000),

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

