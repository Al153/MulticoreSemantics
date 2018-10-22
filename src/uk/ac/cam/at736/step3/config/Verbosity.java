package uk.ac.cam.at736.step3.config;

public enum Verbosity {
    All(0), Batch(1), ThreadProgression(2), ArrayProgression(3), WriteProgression(4);

    public final int level;
    Verbosity(int l){
        this.level = l;
    }
}
