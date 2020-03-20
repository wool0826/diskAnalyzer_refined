package com.wool0826.analyzer;

import com.wool0826.analyzer.service.AnalyzeService;

public class Application {
    public static void main(String[] args) {
        AnalyzeService analyzer = new AnalyzeService();
        analyzer.analyze();
    }
}
