package com.streamsdemo.reports;

import com.streamsdemo.data.SampleData;
import com.streamsdemo.hr.Employee;
import com.streamsdemo.hr.EmployeeAnalytics;
import com.streamsdemo.orders.Order;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** Runnable demo: report building with joining and toMap. */
public class ReportDemo {
    public static void main(String[] args) {
        List<Order> orders = SampleData.orders();
        List<Employee> employees = SampleData.employees();

        System.out.println("=== Report building (Streams) ===");

        List<String> topNames = EmployeeAnalytics.topEarners(employees, 3).stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        System.out.println(ReportBuilder.joinNamesFramed(topNames));

        System.out.println("\nProduct revenue (COMPLETED orders):");
        ReportBuilder.productRevenue(orders).entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(e -> System.out.printf("  %-10s $%.2f%n", e.getKey(), e.getValue()));

        System.out.println("\nNote: toMap NEEDS a merge function when keys can repeat,");
        System.out.println("otherwise it throws IllegalStateException on duplicates.");
    }
}
