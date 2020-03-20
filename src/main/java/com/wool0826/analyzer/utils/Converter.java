package com.wool0826.analyzer.utils;

import com.wool0826.analyzer.entity.Node;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Converter {
    public static List<Node> convertPathToNodeList(Node node, String path) {
        File[] files = new File(path).listFiles();

        if(files == null)
            return null;

        List<File> fileList = Arrays.asList(files);

        return fileList.stream()
                .map(file -> {
                    return Node.builder()
                            .path(file.getAbsolutePath())
                            .name(file.getName())
                            .parent(node)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
