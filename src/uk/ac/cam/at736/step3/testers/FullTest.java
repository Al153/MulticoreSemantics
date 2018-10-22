package uk.ac.cam.at736.step3.testers;

import uk.ac.cam.at736.step3.arrays.LockedSharedArray;
import uk.ac.cam.at736.step3.arrays.ReaderWriterSharedArray;
import uk.ac.cam.at736.step3.arrays.TTSSharedArray;
import uk.ac.cam.at736.step3.arrays.UnsafeSharedArray;
import uk.ac.cam.at736.step3.config.*;
import uk.ac.cam.at736.step3.data.ArraySizeProgressionResult;
import uk.ac.cam.at736.step3.data.FullTestResult;
import uk.ac.cam.at736.step3.data.ThreadCount;

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

    public static void main(String[] args) throws InterruptedException {
        List<TestInstance> cases = new LinkedList<TestInstance>();

        cases.add(new TestInstance("Unsafe", size -> new UnsafeSharedArray(size.getSize())));
        cases.add(new TestInstance("Safe", size -> new LockedSharedArray(size.getSize())));
        cases.add(new TestInstance("TATAS", size -> new TTSSharedArray(size.getSize())));
        cases.add(new TestInstance("ReaderWriter", size -> new ReaderWriterSharedArray(size.getSize())));


        FullTestConfig cfg = new FullTestConfig(
                cases,
                new ArraySize(500),
                new ArraySize(5000),
                500,
                new TestsPerBatch(100),
                new ThreadCount(15),
                new IterationsToComplete(10000),
                true
        );


        FullTest tester = new FullTest(cfg);

        FullTestResult result = tester.doTest();

    }
}

