package com.wool0826.analyzer.Application;

import com.wool0826.analyzer.entity.Node;
import com.wool0826.analyzer.utils.Converter;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Analyzer {
    private Node rootNode;

    public Analyzer() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void analyze() {
        //real
        //String selectedDrive = selectDrive();

        // dev
        String selectedDrive = "E:\\Download";

        rootNode = Node.builder()
                .path(selectedDrive)
                .name(selectedDrive)
                .build();

        rootNode.setList(Converter.convertPathToNodeList(rootNode, selectedDrive));

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(rootNode.getPath() + " " + rootNode.getName() + " " + rootNode.getSize());
    }

    public static String selectDrive() {
        List<File> root = Arrays.asList(File.listRoots());
        List<String> drives = root.stream()
                .filter(File::isDirectory)
                .map(File::toString)
                .collect(Collectors.toList());

        return (String) JOptionPane.showInputDialog(null,
                "Select drive you want to analyze.", "Select Drive", 3, null, drives.toArray(), drives.get(0));
    }
}
