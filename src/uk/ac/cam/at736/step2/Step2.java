package uk.ac.cam.at736.step2;


import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.Value;
import uk.ac.cam.at736.DelayThread;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Step2 {
    private final int N;
    private final int DELAY;
    private final Thread[] threads;

    public Step2(int n, int delay) {
        N = n;
        DELAY = delay;
        this.threads = new Thread[N];
        for (int i = 0; i < N; i++) {
            this.threads[i] = new Thread(new DelayThread(delay, false));
        }

    }

    public long runTest() {
        Instant start = Instant.now();

        for (int i = 0; i < N; i++) {
            this.threads[i].start();
        }
        try {
            for (int i = 0; i < N; i++) {

                this.threads[i].join();

            }
        } catch (InterruptedException e) {
            System.out.println("Caught: " + e);
        }
        Instant end = Instant.now();
        long duration = Duration.between(start, end).toMillis();
        System.out.println("N: " + N +  " elapsed = " + duration);

        return duration;
    }

    public static void main(String[] args) throws IOException {
        Step2Results results = new Step2Results();


        for (int i = 0; i < 100; i ++ ) {
            for (int n = 1; n <= 12; n ++) {
                Step2 harness = new Step2(n, 100);
                results.addResult(n, harness.runTest());
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

        String HOME = System.getProperty("user.home");
        String timestamp = Instant.now().toString().replace(":", "-").split("-\\d\\d\\.")[0];
        String runName = "step2_" + timestamp;
        Path p = Paths.get(HOME, "IdeaProjects", "MulticoreSemantics", "results", "data", runName);
        writer.writeValue(p.toFile(), results);

    }
}


class Step2Results {
    @JsonProperty("results")
    private Map<Integer, List<Long>> results = new HashMap<>();


    public void addResult(int threadCount, long timeTaken){
        if (!results.containsKey(threadCount)){
            results.put(threadCount, new LinkedList<>());
        }

        results.get(threadCount).add(timeTaken);
    }
}