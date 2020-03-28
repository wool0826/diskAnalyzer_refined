package com.wool0826.analyzer.type;

import lombok.Getter;

@Getter
public enum SizeType {
    B(1D, "B"),
    KB(1024D, "KB"),
    MB(1024*1024D, "MB"),
    GB(1024*1024*1024D, "GB")
    ;

    private double size;
    private String code;

    SizeType(double size, String code) {
        this.size = size;
        this.code = code;
    }
}
