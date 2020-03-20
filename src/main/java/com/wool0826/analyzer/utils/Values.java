package com.wool0826.analyzer.utils;

import lombok.Getter;

@Getter
public enum Values {
    B(1, "B"),
    KB(1024, "KB"),
    MB(1024*1024, "MB"),
    GB(1024*1024*1024, "GB"),
    NTHREAD(4, "Number of Thread")
    ;

    private int size;
    private String code;

    Values(int size, String code) {
        this.size = size;
        this.code = code;
    }
}
