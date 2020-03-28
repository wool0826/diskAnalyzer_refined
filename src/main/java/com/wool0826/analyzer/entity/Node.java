package com.wool0826.analyzer.entity;

import com.wool0826.analyzer.type.NodeType;
import com.wool0826.analyzer.type.SizeType;
import com.wool0826.analyzer.utils.Formatter;
import lombok.Builder;
import lombok.Data;
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
    private NodeType nodeType;
    private String name;
    private String path;
    private boolean isDirectory;
    private List<Node> list;

    private Node parent;

    @Builder.Default
    private AtomicLong size = new AtomicLong(0);

    @Builder.Default
    private AtomicInteger finishedCount = new AtomicInteger(0);

    public String toStringWithPostFix(){
        return name + " " + getCalculatedSize();
    }

    public String getCalculatedSize(){
        long size = this.size.get();

        if(size >= SizeType.TB.getSize()) {
            double refinedSize = (size / SizeType.TB.getSize());
            return Formatter.from(refinedSize, SizeType.TB.getCode());
        } else if(size >= SizeType.GB.getSize()) {
            double refinedSize =(size / SizeType.GB.getSize());
            return Formatter.from(refinedSize, SizeType.GB.getCode());
        } else if (size >= SizeType.MB.getSize()) {
            double refinedSize =(size / SizeType.MB.getSize());
            return Formatter.from(refinedSize, SizeType.MB.getCode());
        } else if (size >= SizeType.KB.getSize()) {
            double refinedSize =(size / SizeType.KB.getSize());
            return Formatter.from(refinedSize, SizeType.KB.getCode());
        } else {
            double refinedSize =(size / SizeType.B.getSize());
            return Formatter.from(refinedSize, SizeType.B.getCode());
        }
    }

    public void generateChildList() {
        File file = new File(this.path);

        this.list = (file.listFiles() == null) ?
            new ArrayList<>() : Arrays.asList(file.listFiles()).stream()
                .map(f -> Node.builder()
                    .path(f.getAbsolutePath())
                    .name(f.getName())
                    .parent(this)
                    .isDirectory(!f.isFile() && (f.isDirectory()))
                    .nodeType(isDirectory ? NodeType.DIR : NodeType.FILE)
                    .build())
                .collect(Collectors.toList());
    }

    public void updateParentSize() {
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
        else if(this.list == null) {
            return false;
        }
        else {
            return (this.finishedCount.get() == this.list.size());
        }
    }

    public void setSize(long size) {
        this.size.set(size);
    }
}
