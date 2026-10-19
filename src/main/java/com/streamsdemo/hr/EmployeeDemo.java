package com.streamsdemo.hr;

import com.streamsdemo.data.SampleData;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;

/** Runnable demo: prints HR analytics built with streams. */
public class EmployeeDemo {
    public static void main(String[] args) {
        List<Employee> employees = SampleData.employees();

        System.out.println("=== Employee analytics (Streams) ===");
        System.out.println("\nTop 3 earners:");
        EmployeeAnalytics.topEarners(employees, 3)
                .forEach(e -> System.out.println("  " + e));

        System.out.println("\nAverage salary by department:");
        EmployeeAnalytics.averageSalaryByDept(employees).entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.printf("  %-12s $%,.0f%n", e.getKey(), e.getValue()));

        DoubleSummaryStatistics stats = EmployeeAnalytics.salaryStats(employees);
        System.out.println("\nSalary statistics:");
        System.out.printf("  count=%d  sum=$%,.0f  min=$%,.0f  avg=$%,.0f  max=$%,.0f%n",
                stats.getCount(), stats.getSum(), stats.getMin(),
                stats.getAverage(), stats.getMax());
    }
}
