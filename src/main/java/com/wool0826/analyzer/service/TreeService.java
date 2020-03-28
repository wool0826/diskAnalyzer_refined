package com.wool0826.analyzer.service;

import com.wool0826.analyzer.entity.Node;
import com.wool0826.analyzer.entity.TreeNode;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.ExpandVetoException;

public class TreeService {
    public JFrame showTree(Node rootNode) {
        TreeNode root = new TreeNode(rootNode);
        root.addChild();

        JFrame jframe = new JFrame("Disk Analyzer");
        JTree tree = new JTree(root);

        tree.addTreeSelectionListener((event) -> {
            JTree tempTree = (JTree) event.getSource();
            TreeNode selectedNode = (TreeNode) tempTree
                    .getLastSelectedPathComponent();

            if(selectedNode != null) {
                selectedNode.addChild();
            }
        });

        jframe.add(new JScrollPane(tree));
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setVisible(true);

        return jframe;
    }
}
