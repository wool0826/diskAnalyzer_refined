package com.wool0826.analyzer.service;

import com.wool0826.analyzer.type.NodeType;
import com.wool0826.analyzer.repository.WorkStatus;
import com.wool0826.analyzer.repository.TimeChecker;
import com.wool0826.analyzer.thread.SizeCalculateThread;
import com.wool0826.analyzer.thread.WorkFetchThread;
import com.wool0826.analyzer.entity.Node;
import com.wool0826.analyzer.repository.QueueInCompletedWork;
import com.wool0826.analyzer.thread.WorkRunnable;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AnalyzeService {
    private WorkFetchThread workFetchThread;
    private SizeCalculateThread sizeCalculateThread;
    private TreeService treeService;

    public AnalyzeService() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            workFetchThread = new WorkFetchThread();
            sizeCalculateThread = new SizeCalculateThread();
            treeService = new TreeService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void analyze() {
        String selectedDrive = selectDrive();

        if(selectedDrive == null) {
            return;
        }

        TimeChecker.start();

        Node rootNode = Node.builder()
            .path(selectedDrive)
            .name(selectedDrive)
            .isDirectory(true)
            .nodeType(NodeType.ROOT)
            .build();

        try {
            WorkRunnable runnable = new WorkRunnable(rootNode);
            QueueInCompletedWork.add(runnable);

            workFetchThread.start();
            sizeCalculateThread.start();

            while(!WorkStatus.isFinished()) {
                Thread.sleep(100);
            }

            treeService.showTree(rootNode);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
