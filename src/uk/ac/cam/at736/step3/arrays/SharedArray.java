package uk.ac.cam.at736.step3.arrays;

import java.util.Random;

public abstract class SharedArray {
    protected int[] arr;

    protected SharedArray(int size) {

        arr = new int[size];

        Random rand = new Random();

        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt();
        }
    }

    public abstract int sum();

    public abstract void write(int index, int value);
}
