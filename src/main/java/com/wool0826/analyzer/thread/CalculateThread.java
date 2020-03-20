package com.wool0826.analyzer.thread;

import com.wool0826.analyzer.common.Status;
import com.wool0826.analyzer.entity.Node;
import com.wool0826.analyzer.queue.QueueCompleted;

public class CalculateThread extends Thread {
    @Override
    public void run() {
        while(!Status.isFinished()) {
            Node node = QueueCompleted.get();
            if(node == null) continue;

            if(node.isFinished()) {
                node.updateSizeOnParent();
            } else {
                QueueCompleted.add(node);
            }
        }
    }
}
