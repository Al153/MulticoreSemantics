package uk.ac.cam.at736.step3;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import uk.ac.cam.at736.step3.arrays.*;
import uk.ac.cam.at736.step3.config.*;
import uk.ac.cam.at736.step3.data.FullTestResult;
import uk.ac.cam.at736.step3.data.ThreadCount;
import uk.ac.cam.at736.step3.testers.FullTest;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class RunAllTests {
    public static final WriteEnabled write10k = new WriteEnabled(true, 10000);
    public static final WriteEnabled write100 = new WriteEnabled(true, 100);
    public static final WriteEnabled readOnly = new WriteEnabled(false, 0);
    public static final List<ArraySize> standardArraySize = Collections.singletonList(new ArraySize(1000));


    public static FullTestConfig getCommonTestConfig(List<TestInstance> cases) {
        return new FullTestConfig(
                cases,
                new TestsPerBatch(200),
                new ThreadCount(16),
                new IterationsToComplete(200000),
                true
        );
    }

    public static final List<TestInstance> readonlyCases =
            Arrays.asList(
                    new TestInstance(
                            "Unsafe",
                            size -> new UnsafeSharedArray(size.getSize()),
                            Arrays.asList(new ArraySize(5), new ArraySize(1000), new ArraySize(5000)),
                            readOnly
                    ),
                    new TestInstance(
                            "Mutex",
                            size -> new LockedSharedArray(size.getSize()),
                            Arrays.asList(new ArraySize(5), new ArraySize(1000), new ArraySize(5000)),
                            readOnly
                    ),
                    new TestInstance(
                            "TATAS",
                            size -> new TTSSharedArray(size.getSize()),
                            Arrays.asList(new ArraySize(5), new ArraySize(1000), new ArraySize(5000)),
                            readOnly
                    ),
                    new TestInstance(
                            "ReaderWriter",
                            size -> new ReaderWriterSharedArray(size.getSize()),
                            Arrays.asList(new ArraySize(5), new ArraySize(1000), new ArraySize(5000)),
                            readOnly
                    ),
                    new TestInstance(
                            "Flags",
                            size -> new FlagBasedSharedArray(size.getSize(), 20),
                            Arrays.asList(new ArraySize(5), new ArraySize(1000), new ArraySize(5000)),
                            readOnly
                    )
            );

    public static final List<TestInstance> writeCases =
            Arrays.asList(
                    new TestInstance(
                            "Write_Unsafe_10k",
                            size -> new UnsafeSharedArray(size.getSize()),
                            standardArraySize,
                            write10k
                    ),
                    new TestInstance(
                            "Write_Unsafe_100",
                            size -> new UnsafeSharedArray(size.getSize()),
                            standardArraySize,
                            write100
                    ),

                    new TestInstance(
                            "Write_Mutex_10k",
                            size -> new LockedSharedArray(size.getSize()),
                            standardArraySize,
                            write10k
                    ),


                    new TestInstance(
                            "Write_Mutex_100",
                            size -> new LockedSharedArray(size.getSize()),
                            standardArraySize,
                            write100
                    ),


                    new TestInstance(
                            "Write_TATAS_10k",
                            size -> new TTSSharedArray(size.getSize()),
                            standardArraySize,
                            write10k
                    ),


                    new TestInstance(
                            "Write_TATAS_100",
                            size -> new TTSSharedArray(size.getSize()),
                            standardArraySize,
                            write100
                    ),


                    new TestInstance(
                            "Write_RW_10k",
                            size -> new ReaderWriterSharedArray(size.getSize()),
                            standardArraySize,
                            write10k
                    ),


                    new TestInstance(
                            "Write_RW_100",
                            size -> new ReaderWriterSharedArray(size.getSize()),
                            standardArraySize,
                            write100
                    ),

                    new TestInstance(
                            "Write_Flags_10k",
                            size -> new UnsafeSharedArray(size.getSize()),
                            standardArraySize,
                            write10k
                    ),

                    new TestInstance(
                            "Write_Flags_100",
                            size -> new UnsafeSharedArray(size.getSize()),
                            standardArraySize,
                            write100
                    )
            );


    public static void main(String[] args) throws InterruptedException, IOException {
        List<TestInstance> cases = new LinkedList<>();
        cases.addAll(readonlyCases);
        cases.addAll(writeCases);

        FullTest tester = new FullTest(getCommonTestConfig(cases));

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

