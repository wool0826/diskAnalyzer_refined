package com.wool0826.analyzer.utils;

import java.text.DecimalFormat;

public class Formatter {
    private static DecimalFormat format = new DecimalFormat("#.###");

    public static String from(double size, String code) {
        return format.format(size) + " " + code;
    }
}
