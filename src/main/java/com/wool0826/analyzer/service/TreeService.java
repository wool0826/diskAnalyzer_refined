package com.wool0826.analyzer.service;

import com.wool0826.analyzer.entity.Node;
import com.wool0826.analyzer.entity.TreeNode;

import javax.swing.*;
import java.awt.*;

public class TreeService {
    public void showTree(Node rootNode) {
        TreeNode root = new TreeNode(rootNode);
        root.addChildTreeNode();

        JFrame jframe = new JFrame("Disk Analyzer");
        JTree tree = new JTree(root);
        tree.setCellRenderer(new TreeRenderService());
        tree.setRowHeight(20);

        jframe.setSize(new Dimension(500,800));
        jframe.add(new JScrollPane(tree));
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);
    }
}
