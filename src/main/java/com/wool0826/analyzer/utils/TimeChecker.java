package com.wool0826.analyzer.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class TimeChecker {
    private static Instant startedTime;
    private static Instant completedTime;

    public static void start() {
        startedTime = Instant.now();
    }

    public static String end() {
        completedTime = Instant.now();

        long hours = ChronoUnit.HOURS.between(startedTime, completedTime);
        long minutes = ChronoUnit.MINUTES.between(startedTime, completedTime);
        long seconds = ChronoUnit.SECONDS.between(startedTime, completedTime);

        return "[Elapsed Time] : " + hours + " hours, " + minutes + "minutes, " + seconds + "seconds";
    }
}
