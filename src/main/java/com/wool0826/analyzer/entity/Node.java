package com.wool0826.analyzer.entity;

import com.wool0826.analyzer.type.NodeType;
import com.wool0826.analyzer.type.SizeType;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static j2html.TagCreator.li;

@Data
@Builder
@ToString(exclude = "parent")
public class Node {
    private String name;
    private String path;
    private Node parent;
    private boolean isDirectory;
    private List<Node> list;
    private NodeType nodeType;

    @Builder.Default
    private AtomicLong size = new AtomicLong(0);

    @Builder.Default
    private AtomicInteger finishedCount = new AtomicInteger(0);

    public String toText(){
        return name + " " + getCalculatedSize();
    }

    public String getCalculatedSize(){
        long size = this.size.get();
        DecimalFormat decimalFormat = new DecimalFormat("#.###");

        if(size/1000.0 >= SizeType.GB.getSize()) {
            double refinedSize = (size / SizeType.GB.getSize()) / 1000;
            return decimalFormat.format(refinedSize) + " TB";
        } else if(size >= SizeType.GB.getSize()) {
            double refinedSize =(size / SizeType.GB.getSize());
            return decimalFormat.format(refinedSize) + " " + SizeType.GB.getCode();
        } else if (size >= SizeType.MB.getSize()) {
            double refinedSize =(size / SizeType.MB.getSize());
            return decimalFormat.format(refinedSize) + " " + SizeType.MB.getCode();
        } else if (size >= SizeType.KB.getSize()) {
            double refinedSize =(size / SizeType.KB.getSize());
            return decimalFormat.format(refinedSize) + " " + SizeType.KB.getCode();
        } else {
            double refinedSize =(size / SizeType.B.getSize());
            return decimalFormat.format(refinedSize) + " " + SizeType.B.getCode();
        }
    }

    public void generateList() {
        File file = new File(this.path);

        if(file.listFiles() == null) {
            this.list = new ArrayList<>();
            return;
        }

        this.list = Arrays.asList(file.listFiles()).stream()
            .map(f -> Node.builder()
                .path(f.getAbsolutePath())
                .name(f.getName())
                .parent(this)
                .isDirectory(!f.isFile() && (f.isDirectory()))
                .nodeType(isDirectory ? NodeType.DIR : NodeType.FILE)
                .build())
            .collect(Collectors.toList());
    }

    public void updateSizeOnParent() {
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
