package com.wool0826.analyzer.service;

import com.wool0826.analyzer.entity.Node;
import com.wool0826.analyzer.entity.TreeNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class TreeRenderService extends DefaultTreeCellRenderer {
    private static final String SPAN_FORMAT = "<span style='color:%s;'>%s</span>";

    private ImageIcon fileIcon;

    public TreeRenderService() {
        fileIcon = new ImageIcon(getClass().getResource("/file.jpg"));
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        TreeNode treeNode = (TreeNode) value;
        Node node = treeNode.getNode();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<html> [")
            .append(String.format(SPAN_FORMAT, "orange", node.getCalculatedSize()))
            .append("] ")
            .append(String.format(SPAN_FORMAT, "black", node.getName()))
            .append("</html>");

        this.setText(stringBuilder.toString());

        if(treeNode.isLeaf()) {
            this.setIcon(fileIcon);
        }

        return this;
    }
}
