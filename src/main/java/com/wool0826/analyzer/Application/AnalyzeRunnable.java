package com.wool0826.analyzer.Application;

import com.wool0826.analyzer.entity.Node;
import com.wool0826.analyzer.utils.Converter;
import lombok.AllArgsConstructor;

import java.io.File;
import java.util.List;

@AllArgsConstructor
public class AnalyzeRunnable implements Runnable {
    private Node node;

    @Override
    public void run() {
        File file = new File(node.getPath());

        if(!file.isDirectory()) {
            node.setSize(file.length());
            node.updateSizeOnParent();
        } else {
            List<Node> nodeList = Converter.convertPathToNodeList(node, node.getPath());

            if(nodeList == null) {
                return;
            }

            node.setList(nodeList);

            for (Node child : nodeList) {
                AnalyzeRunnable runnable = new AnalyzeRunnable(child);
                ThreadPool.submit(runnable);
            }            /*
                TODO 디렉토리일 때 자기의 값을 다 구한 다음, 부모에 적용하는 과정이 필요
                ThreadPool이 아니라 새로 정의한 큐에 작업들을 넣어서 처리하는 게 좋을 것 같음.
             */
        }
    }
}
