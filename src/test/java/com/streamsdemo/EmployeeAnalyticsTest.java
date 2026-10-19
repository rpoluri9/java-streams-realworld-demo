package com.streamsdemo;

import com.streamsdemo.data.SampleData;
import com.streamsdemo.hr.Employee;
import com.streamsdemo.hr.EmployeeAnalytics;
import org.junit.jupiter.api.Test;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeAnalyticsTest {

    private final List<Employee> employees = SampleData.employees();

    @Test
    void topEarnersReturnsHighestSalariesFirst() {
        List<String> names = EmployeeAnalytics.topEarners(employees, 3).stream()
                .map(Employee::getName).collect(Collectors.toList());
        assertEquals(List.of("Priya", "Anil", "Vikram"), names);
    }

    @Test
    void topEarnersRespectsLimit() {
        assertEquals(2, EmployeeAnalytics.topEarners(employees, 2).size());
    }

    @Test
    void averageSalaryByDept() {
        Map<String, Double> avg = EmployeeAnalytics.averageSalaryByDept(employees);
        assertEquals(115000.0, avg.get("Engineering"), 0.001);
        assertEquals(75000.0, avg.get("Sales"), 0.001);
        assertEquals(77500.0, avg.get("HR"), 0.001);
    }

    @Test
    void salaryStats() {
        DoubleSummaryStatistics stats = EmployeeAnalytics.salaryStats(employees);
        assertEquals(8, stats.getCount());
        assertEquals(765000.0, stats.getSum(), 0.001);
        assertEquals(65000.0, stats.getMin(), 0.001);
        assertEquals(135000.0, stats.getMax(), 0.001);
        assertEquals(95625.0, stats.getAverage(), 0.001);
    }
}
