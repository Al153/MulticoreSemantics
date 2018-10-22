package uk.ac.cam.at736.step3.data;

import lombok.Value;

@Value
public class BatchTestResult {
    long[] results;

    double getMean() {
        long t = 0;
        for (long r : results) {
            t += r;
        }

        return ((double) t) / results.length;
    }

    double getStandardDeviation() {
        double mu = getMean();
        double sd = 0;

        for (int i = 0; i < results.length; i++) {
            sd += Math.pow(results[i] - mu, 2);
        }

        return sd / results.length;
    }

    String prettyPrint() {
        return "Batch( mean: " + getMean() + ", std" + getStandardDeviation() + ")";
    }
}
