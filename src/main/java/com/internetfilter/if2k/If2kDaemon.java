package com.internetfilter.if2k;

import com.internetfilter.if2k.kernel.*;

public class If2kDaemon extends Thread {

    Kernel if2k_kernel;
    long secs = 0;

    public If2kDaemon() {
        if2k_kernel = null; // new Kernel();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        If2kDaemon r = new If2kDaemon();

        r.start();

        try {
            r.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep(1000);
                secs++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public long getSecs() {
        return secs;
    }
}
