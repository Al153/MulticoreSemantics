package uk.ac.cam.at736.step1;

import uk.ac.cam.at736.DelayThread;

import java.util.concurrent.atomic.AtomicInteger;

public class Step1 {
    // Shared variable for use with example atomic compare and swap
    // operations (ai.compareAndSet in this example).

    static AtomicInteger ai = new AtomicInteger(0);

    // Step1 function

    public static void main(String args[]) {
        int N = Integer.parseInt(args[1]);

        // Start a new thread, and then wait for it to complete:

        System.out.println("Start");
        try {
            DelayThread e1 = new DelayThread(100, true);
            Thread t1 = new Thread(e1);
            t1.start();
            t1.join();
            System.out.println("Joined with thread, ret=" + e1.result);
        } catch (InterruptedException ie) {
            System.out.println("Caught " + ie);
        }
        System.out.println("End");

        // DelayThread compare and swap operations

        boolean s;
        System.out.println("ai=" + ai);
        s = ai.compareAndSet(0, 1);
        System.out.println("ai=" + ai + " s=" + s);
        s = ai.compareAndSet(0, 2);
        System.out.println("ai=" + ai + " s=" + s);
        s = ai.compareAndSet(1, 2);
        System.out.println("ai=" + ai + " s=" + s);
    }
}
