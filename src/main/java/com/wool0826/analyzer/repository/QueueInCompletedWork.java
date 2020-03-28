package com.wool0826.analyzer.repository;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueInCompletedWork {
    private static Queue<Runnable> queue = new ConcurrentLinkedQueue<>();

    public static Runnable get() {
        return queue.poll();
    }

    public static void add(Runnable runnable) {
        queue.offer(runnable);
    }
}
