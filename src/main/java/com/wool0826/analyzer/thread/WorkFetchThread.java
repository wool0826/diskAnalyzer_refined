package com.wool0826.analyzer.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.wool0826.analyzer.repository.QueueInCompletedWork;
import com.wool0826.analyzer.repository.WorkStatus;
import lombok.Synchronized;

public class WorkFetchThread extends Thread {
    private final int numberThread = 4;
    private ExecutorService executorService = Executors.newFixedThreadPool(numberThread);

    @Override
    public void run() {
        while (!WorkStatus.isFinished()) {
            Runnable runnable = QueueInCompletedWork.get();
            if (runnable == null) {
                try {
                    sleep(100);
                } catch (InterruptedException exception) {
                    interrupt();
                }
            } else {
                executorService.submit(runnable);
            }
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
