package com.wool0826.analyzer.thread;

import com.wool0826.analyzer.repository.WorkStatus;
import com.wool0826.analyzer.type.NodeType;
import com.wool0826.analyzer.repository.TimeChecker;
import com.wool0826.analyzer.entity.Node;
import com.wool0826.analyzer.repository.QueueCompletedNode;
import lombok.Synchronized;

public class SizeCalculateThread extends Thread {

    @Override
    public void run() {
        while(!WorkStatus.isFinished()) {
            Node node = QueueCompletedNode.get();

            if(node == null) {
                try {
                    sleep(100);
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                }
            } else if(node.isFinished()) {
                node.updateParentSize();

                if(node.getNodeType() == NodeType.ROOT) {
                    WorkStatus.setFinished();
                    System.out.println(TimeChecker.end());
                    System.out.println(node.toStringWithPostFix());
                }
            } else {
                QueueCompletedNode.add(node);
            }
        }
    }
}
