package uk.ac.cam.at736.step3;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import uk.ac.cam.at736.step3.data.FullTestResult;
import uk.ac.cam.at736.step3.testers.FullTest;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

import static uk.ac.cam.at736.step3.RunAllTests.getCommonTestConfig;
import static uk.ac.cam.at736.step3.RunAllTests.writeCases;

public class RunWriteTestsOnly {
    public static void main(String[] args) throws InterruptedException, IOException {


        FullTest tester = new FullTest(getCommonTestConfig(writeCases));

        FullTestResult result = tester.doTest();

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

        String HOME = System.getProperty("user.home");
        String timestamp = Instant.now().toString().replace(":", "-").split("-\\d\\d\\.")[0];
        String runName = "write_" + timestamp;
        Path p = Paths.get(HOME, "IdeaProjects", "MulticoreSemantics", "results", "data", runName);
        writer.writeValue(p.toFile(), result);
    }
}
