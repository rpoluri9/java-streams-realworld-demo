package com.streamsdemo.logs;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/** Log analysis with streams: the classic ops use-case. */
public final class LogAnalyzer {

    private LogAnalyzer() {}

    /** Parse raw lines into entries. */
    public static List<LogEntry> parseAll(List<String> lines) {
        return lines.stream().map(LogEntry::parse).collect(Collectors.toList());
    }

    /** All ERROR entries. */
    public static List<LogEntry> errors(List<LogEntry> entries) {
        return entries.stream()
                .filter(e -> e.getLevel() == LogLevel.ERROR)
                .collect(Collectors.toList());
    }

    /** How many ERRORs happened in each hour of day. */
    public static Map<Integer, Long> errorCountByHour(List<LogEntry> entries) {
        return entries.stream()
                .filter(e -> e.getLevel() == LogLevel.ERROR)
                .collect(Collectors.groupingBy(
                        e -> e.getTimestamp().getHour(),
                        Collectors.counting()));
    }

    /** The single most frequent ERROR message. */
    public static Optional<String> mostFrequentErrorMessage(List<LogEntry> entries) {
        return entries.stream()
                .filter(e -> e.getLevel() == LogLevel.ERROR)
                .collect(Collectors.groupingBy(LogEntry::getMessage, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }
}
