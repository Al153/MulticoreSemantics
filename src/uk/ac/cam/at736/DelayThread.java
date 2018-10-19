package uk.ac.cam.at736;

// To compile:
//
// javac DelayThread.java

import lombok.Getter;

public class DelayThread implements Runnable {

    // Delay function waits a variable time controlled by "d".  The function
    // writes to a per-object volatile field -- this aims to prevent the compiler
    // from optimizing the delay away completely.

    volatile int temp;

    void delay(int arg) {
        for (int i = 0; i < arg; i++) {
            for (int j = 0; j < 1000000; j++) {
                this.temp += i + j;
            }
        }
    }

    // Constructor for an "DelayThread" object.  Fields in the object can be
    // used to pass values to/from the thread when it is started and
    // when it finishes.


    private int arg;
    public int result;
    @Getter
    boolean print;

    public DelayThread(int arg, boolean p) {
        this.arg = arg;
        this.print = p;
    }

    // DelayThread thread function.  This just delays for a little while,
    // controlled by the parameter passed when the thread is started.

    public void run() {
        if (print) System.out.println("Thread started arg=");
        delay(arg);
        result = 42;
        if (print) System.out.println("Thread done result=" + result);
    }


}
