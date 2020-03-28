package com.wool0826.analyzer.type;

import lombok.Getter;

@Getter
public enum NodeType {
    ROOT("ROOT"),
    DIR("DIR"),
    FILE("FILE");

    private String type;

    NodeType(String type) {
        this.type = type;
    }
}
