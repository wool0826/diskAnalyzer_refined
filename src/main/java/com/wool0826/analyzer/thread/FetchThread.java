package com.wool0826.analyzer.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.wool0826.analyzer.common.Status;
import com.wool0826.analyzer.common.Values;
import com.wool0826.analyzer.queue.QueueWorking;

public class FetchThread extends Thread {
    ExecutorService executorService = Executors.newFixedThreadPool(Values.NTHREAD.getSize());

    @Override
    public void run() {
        while (!Status.isFinished()) {
            Runnable runnable = QueueWorking.get();
            if (runnable == null)
                continue;

            executorService.submit(runnable);
        }

        executorService.shutdown();

        try {
            while (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {}
        } catch (Exception e) {
            e.printStackTrace();
            executorService.shutdownNow();
        }
    }
}
