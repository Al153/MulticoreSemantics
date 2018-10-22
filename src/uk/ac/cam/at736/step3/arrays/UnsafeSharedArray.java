package uk.ac.cam.at736.step3.arrays;

public final class UnsafeSharedArray extends SharedArray {
    public UnsafeSharedArray(int x) {
        super(x);
    }

    @Override
    public int sum(int threadNo) {
        int total = 0;
        for (int i = 0; i < arr.length; i++) {
            total += i;
        }

        return total;
    }

    @Override
    public void write(int index, int value, int threadNo) {
        arr[index % arr.length] = value;
    }
}
