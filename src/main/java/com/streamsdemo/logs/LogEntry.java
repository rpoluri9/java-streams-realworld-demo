package com.streamsdemo.logs;

import java.time.LocalDateTime;

/** One parsed log line. */
public class LogEntry {
    private final LocalDateTime timestamp;
    private final LogLevel level;
    private final String message;

    public LogEntry(LocalDateTime timestamp, LogLevel level, String message) {
        this.timestamp = timestamp;
        this.level = level;
        this.message = message;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public LogLevel getLevel() { return level; }
    public String getMessage() { return message; }

    /** Parses lines like "2026-10-19T09:15:30 ERROR Database connection failed". */
    public static LogEntry parse(String line) {
        String[] parts = line.split(" ", 3);
        return new LogEntry(
                LocalDateTime.parse(parts[0]),
                LogLevel.valueOf(parts[1]),
                parts[2].trim());
    }

    @Override
    public String toString() {
        return timestamp + " " + level + " " + message;
    }
}
