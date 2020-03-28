package com.wool0826.analyzer.entity;

import lombok.Getter;

import javax.swing.tree.DefaultMutableTreeNode;

@Getter
public class TreeNode extends DefaultMutableTreeNode {
    private Node node;
    private boolean isExpanded;

    public TreeNode(Node node) {
        super(node.toStringWithPostFix());
        this.node = node;
        this.isExpanded = false;
    }

    @Override
    public boolean isLeaf() {
       return node.getList() == null;
    }

    public void addChildTreeNode() {
        if(!node.isDirectory() || isExpanded)
            return;

        node.getList().stream()
            .map(TreeNode::new)
            .forEach(node -> {
                node.addChildTreeNode();
                this.add(node);
            });
    }
}
