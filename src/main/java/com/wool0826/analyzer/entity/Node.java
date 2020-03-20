package com.wool0826.analyzer.entity;

import com.wool0826.analyzer.utils.Values;
import lombok.Builder;
import lombok.Data;
import lombok.Synchronized;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@ToString(exclude = "parent")
public class Node {
    private String name;
    private String path;
    private long size;
    private Node parent;

    @Builder.Default
    private List<Node> list = new ArrayList<>();

    public String getCalculatedSize(){
        if(this.size >= Values.GB.getSize()) {
            return (this.size / Values.GB.getSize()) + " " + Values.GB.getCode();
        } else if (this.size >= Values.MB.getSize()) {
            return (this.size / Values.MB.getSize()) + " " + Values.MB.getCode();
        } else if (this.size >= Values.KB.getSize()) {
            return (this.size / Values.KB.getSize()) + " " + Values.KB.getCode();
        } else {
            return this.size + " " + Values.B.getCode();
        }
    }

    public String toText(){
        return name + " " + getCalculatedSize();
    }

    public void add(Node node) {
        this.list.add(node);
    }

    @Synchronized
    public void updateSizeOnParent() {
        this.parent.size += this.size;
    }
}
