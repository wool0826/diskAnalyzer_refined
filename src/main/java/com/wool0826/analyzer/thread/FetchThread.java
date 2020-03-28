package com.wool0826.analyzer.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.wool0826.analyzer.queue.QueueWorking;

public class FetchThread extends Thread {
    private final int numberThread = 4;
    private ExecutorService executorService = Executors.newFixedThreadPool(numberThread);

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
