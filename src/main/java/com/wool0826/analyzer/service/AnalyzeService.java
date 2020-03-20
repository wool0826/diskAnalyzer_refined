package com.wool0826.analyzer.service;

import com.wool0826.analyzer.common.Status;
import com.wool0826.analyzer.thread.CalculateThread;
import com.wool0826.analyzer.thread.FetchThread;
import com.wool0826.analyzer.entity.Node;
import com.wool0826.analyzer.queue.QueueWorking;
import com.wool0826.analyzer.thread.RunnableWorker;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AnalyzeService {
    private Node rootNode;
    private FetchThread fetchThread;
    private CalculateThread calculateThread;

    public AnalyzeService() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            fetchThread = new FetchThread();
            calculateThread = new CalculateThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void analyze() {
        //real
        //String selectedDrive = selectDrive();

        // dev
        String selectedDrive = "/Users/user/Downloads";

        if(selectedDrive == null) {
            return;
        }

        rootNode = Node.builder()
                .path(selectedDrive)
                .name(selectedDrive)
                .isDirectory(true)
                .build();

        try {
            RunnableWorker runnable = new RunnableWorker(rootNode);
            QueueWorking.add(runnable);

            fetchThread.start();
            calculateThread.start();

            while(!rootNode.isFinished()){}
            Status.setFinished();
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
