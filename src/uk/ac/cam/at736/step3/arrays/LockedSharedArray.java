package uk.ac.cam.at736.step3.arrays;

public final class LockedSharedArray extends SharedArray {

    public LockedSharedArray(int size) {
        super(size);
    }


    @Override
    public int sum(int threadNo) {
        synchronized (this) {
            int total = 0;
            for (int i = 0; i < arr.length; i++) {
                total += i;
            }

            return total;
        }
    }

    @Override
    public void write(int index, int value, int threadNo) {
        synchronized (this) {
            arr[index % arr.length] = value;
        }
    }
}
