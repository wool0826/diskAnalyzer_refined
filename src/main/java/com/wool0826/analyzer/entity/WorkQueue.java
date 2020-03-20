package com.wool0826.analyzer.entity;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class WorkQueue {
    private static Queue<Runnable> queue = new ConcurrentLinkedQueue<>();
    private static AtomicBoolean isFinished = new AtomicBoolean(false);

    public static void setFinished() {
        isFinished.compareAndSet(false, true);
    }

    public static boolean isFinished() {
        return isFinished.get();
    }

    public static Runnable get() {
        if(queue.isEmpty()) return null;
        else return queue.poll();
    }

    public static void add(Runnable runnable) {
        queue.add(runnable);
    }
}
