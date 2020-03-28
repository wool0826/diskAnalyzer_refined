package com.wool0826.analyzer.thread;

import com.wool0826.analyzer.type.NodeType;
import com.wool0826.analyzer.utils.TimeChecker;
import com.wool0826.analyzer.entity.Node;
import com.wool0826.analyzer.queue.QueueCompleted;

public class CalculateThread extends Thread {
    @Override
    public void run() {
        while(!Status.isFinished()) {
            Node node = QueueCompleted.get();

            if(node == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                }
            } else if(node.isFinished()) {
                node.updateSizeOnParent();

                if(node.getNodeType() == NodeType.ROOT) {
                    Status.setFinished();
                    System.out.println(TimeChecker.end());
                    System.out.println(node.toText());
                }
            } else {
                QueueCompleted.add(node);
            }
        }
    }
}
