package uk.ac.cam.at736.step3.arrays;

import uk.ac.cam.at736.step3.arrays.SharedArray;

public final class UnsafeSharedArray extends SharedArray {
    @Override
    public int sum() {
        int total = 0;
        for (int i = 0; i < arr.length; i++) {
            total += i;
        }

        return total;
    }

    @Override
    public void write(int index, int value) {
        arr[index] = value;
    }

    public UnsafeSharedArray(int x) {
        super(x);
    }
}
