package com.wool0826.analyzer.repository;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.wool0826.analyzer.entity.Node;

public class QueueCompletedNode {
    public static Queue<Node> queue = new ConcurrentLinkedQueue<>();

    public static Node get() {
        return queue.poll();
    }

    public static void add(Node node) {
        queue.offer(node);
    }
}
