package com.wool0826.analyzer.thread;

import java.io.File;
import java.util.List;

import com.wool0826.analyzer.entity.Node;
import com.wool0826.analyzer.repository.QueueCompletedNode;
import com.wool0826.analyzer.repository.QueueInCompletedWork;
import lombok.Synchronized;

public class WorkRunnable implements Runnable {
    Node node;

    public WorkRunnable(Node node) {
        this.node = node;
    }

    @Override
    public void run() {
        if(node.isDirectory()) {
            node.generateChildList();

            List<Node> list = node.getList();

            if(list.isEmpty()) {
                node.setSize(new File(node.getPath()).length());
                node.updateParentSize();
                return;
            }

            for(Node currentNode : list) {
                WorkRunnable runnable = new WorkRunnable(currentNode);
                QueueInCompletedWork.add(runnable);
            }

            QueueCompletedNode.add(node);
        } else {
            node.setSize(new File(node.getPath()).length());
            node.updateParentSize();
        }
    }
}
