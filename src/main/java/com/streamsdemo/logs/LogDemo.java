package com.streamsdemo.logs;

import com.streamsdemo.data.SampleData;

import java.util.List;
import java.util.Map;

/** Runnable demo: log triage report built with streams. */
public class LogDemo {
    public static void main(String[] args) {
        List<LogEntry> entries = LogAnalyzer.parseAll(SampleData.logLines());

        System.out.println("=== Log analysis (Streams) ===");
        System.out.println("Parsed " + entries.size() + " log lines.");

        List<LogEntry> errors = LogAnalyzer.errors(entries);
        System.out.println("\nERROR entries (" + errors.size() + "):");
        errors.forEach(e -> System.out.println("  " + e));

        System.out.println("\nERROR count by hour:");
        LogAnalyzer.errorCountByHour(entries).entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.println("  " + String.format("%02d:00", e.getKey())
                        + " -> " + e.getValue()));

        System.out.println("\nMost frequent error: "
                + LogAnalyzer.mostFrequentErrorMessage(entries).orElse("(none)"));
    }
}
