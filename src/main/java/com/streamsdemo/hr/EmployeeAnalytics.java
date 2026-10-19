package com.streamsdemo.hr;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** HR analytics: ranking, averages and summary statistics via streams. */
public final class EmployeeAnalytics {

    private EmployeeAnalytics() {}

    /** Top-N earners: sort descending, then limit. */
    public static List<Employee> topEarners(List<Employee> employees, int n) {
        return employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    /** Average salary per department. */
    public static Map<String, Double> averageSalaryByDept(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDept,
                        Collectors.averagingDouble(Employee::getSalary)));
    }

    /** One-shot statistics: count, sum, min, max, average. */
    public static DoubleSummaryStatistics salaryStats(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));
    }
}
