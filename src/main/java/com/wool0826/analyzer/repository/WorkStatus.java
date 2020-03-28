package com.wool0826.analyzer.repository;

import java.util.concurrent.atomic.AtomicBoolean;

public class WorkStatus {
    private static AtomicBoolean finished = new AtomicBoolean(false);

    public static boolean isFinished() {
        return finished.get();
    }

    public static void setFinished() {
        finished.compareAndSet(false, true);
    }
}
