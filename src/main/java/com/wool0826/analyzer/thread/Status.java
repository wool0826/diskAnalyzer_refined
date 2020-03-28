package com.wool0826.analyzer.thread;

import java.util.concurrent.atomic.AtomicBoolean;

public class Status {
    private static AtomicBoolean finished = new AtomicBoolean(false);

    public static boolean isFinished() {
        return finished.get();
    }

    public static void setFinished() {
        finished.compareAndSet(false, true);
    }
}
