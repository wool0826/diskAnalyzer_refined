package com.wool0826.analyzer.thread;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.wool0826.analyzer.entity.Node;
import com.wool0826.analyzer.queue.QueueCompleted;
import com.wool0826.analyzer.queue.QueueWorking;

public class RunnableWorker implements Runnable {
    Node node;

    public RunnableWorker(Node node) {
        this.node = node;
    }
    @Override
    public void run() {
        if(node.isDirectory()) {
            node.generateList();

            List<Node> list = node.getList();

            if(list.isEmpty()) {
                node.setSize(new File(node.getPath()).length());
                node.updateSizeOnParent();
                return;
            }

            for(Node currentNode : list) {
                RunnableWorker runnable = new RunnableWorker(currentNode);
                QueueWorking.add(runnable);
            }

            QueueCompleted.add(node);
        } else {
            node.setSize(new File(node.getPath()).length());
            node.updateSizeOnParent();
        }
    }
}
