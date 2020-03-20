package com.wool0826.analyzer.entity;

public class WorkThread extends Thread {
    private Node node;

    public WorkThread(Node node) {
        this.node = node;
    }

    @Override
    public void run() {
        while(!WorkQueue.isFinished()) {
            Runnable runnable = WorkQueue.get();


        }
    }
}
