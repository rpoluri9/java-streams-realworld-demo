package com.streamsdemo;

import com.streamsdemo.data.SampleData;
import com.streamsdemo.logs.LogAnalyzer;
import com.streamsdemo.logs.LogEntry;
import com.streamsdemo.logs.LogLevel;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LogAnalyzerTest {

    private final List<LogEntry> entries = LogAnalyzer.parseAll(SampleData.logLines());

    @Test
    void parsesAllLines() {
        assertEquals(10, entries.size());
        LogEntry first = entries.get(0);
        assertEquals(LogLevel.INFO, first.getLevel());
        assertEquals("Application started", first.getMessage());
        assertEquals(9, first.getTimestamp().getHour());
    }

    @Test
    void errorsFiltersOnlyErrors() {
        List<LogEntry> errors = LogAnalyzer.errors(entries);
        assertEquals(4, errors.size());
        assertTrue(errors.stream().allMatch(e -> e.getLevel() == LogLevel.ERROR));
    }

    @Test
    void errorCountByHour() {
        Map<Integer, Long> byHour = LogAnalyzer.errorCountByHour(entries);
        assertEquals(2L, byHour.get(9));
        assertEquals(1L, byHour.get(10));
        assertEquals(1L, byHour.get(11));
    }

    @Test
    void mostFrequentErrorMessage() {
        assertEquals("Database connection failed",
                LogAnalyzer.mostFrequentErrorMessage(entries).orElseThrow());
    }
}
