package com.wool0826.analyzer.entity;

import lombok.Getter;

import javax.swing.tree.DefaultMutableTreeNode;

@Getter
public class TreeNode extends DefaultMutableTreeNode {
    private Node node;
    private boolean isAdded;

    public TreeNode(Node node) {
        super(node.toText());
        this.node = node;
        this.isAdded = false;
    }

    @Override
    public boolean isLeaf() {
       return node.getList() == null;
    }

    public void addChild() {
        if(!node.isDirectory() || isAdded)
            return;

        node.getList().stream()
            .map(TreeNode::new)
            .forEach(node -> {
                node.addChild();
                this.add(node);
            });
    }
}
