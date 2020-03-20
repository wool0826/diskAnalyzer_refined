package com.wool0826.analyzer.entity;

import com.wool0826.analyzer.common.Values;
import lombok.Builder;
import lombok.Data;
import lombok.Synchronized;
import lombok.ToString;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Data
@Builder
@ToString(exclude = "parent")
public class Node {
    private String name;
    private String path;
    private Node parent;
    private boolean isDirectory;
    private List<Node> list;

    @Builder.Default
    private AtomicLong size = new AtomicLong(0);

    @Builder.Default
    private AtomicInteger finishedCount = new AtomicInteger(0);

    public String toText(){
        return name + " " + getCalculatedSize();
    }

    public String getCalculatedSize(){
        long size = this.size.get();

        if(size >= Values.GB.getSize()) {
            return (size / Values.GB.getSize()) + " " + Values.GB.getCode();
        } else if (size >= Values.MB.getSize()) {
            return (size / Values.MB.getSize()) + " " + Values.MB.getCode();
        } else if (size >= Values.KB.getSize()) {
            return (size / Values.KB.getSize()) + " " + Values.KB.getCode();
        } else {
            return size + " " + Values.B.getCode();
        }
    }

    public void generateList() {
        File file = new File(this.path);

        if(file.listFiles() == null) {
            this.list = new ArrayList<>();
        }

        this.list = Arrays.asList(file.listFiles()).stream()
            .map(f -> Node.builder()
                .path(f.getAbsolutePath())
                .name(f.getName())
                .parent(this)
                .isDirectory(f.isDirectory())
                .build())
            .collect(Collectors.toList());
    }

    public void updateSizeOnParent() {
        System.out.println(toText());

        if(this.parent == null){
            return;
        }

        this.parent.size.addAndGet(this.size.get());
        this.parent.finishedCount.incrementAndGet();
    }

    public boolean isFinished() {
        if(!isDirectory) {
            return true;
        }
        else {
            return (this.finishedCount.get() == this.list.size());
        }
    }

    public long getSize() {
        return this.size.get();
    }

    public void setSize(long size) {
        this.size.set(size);
    }
}
