package com.streamsdemo.orders;

import com.streamsdemo.data.SampleData;

import java.util.List;
import java.util.Map;

/** Runnable demo: prints an e-commerce revenue report built with streams. */
public class OrderDemo {
    public static void main(String[] args) {
        List<Order> orders = SampleData.orders();

        System.out.println("=== E-commerce order report (Streams) ===");
        System.out.println("All orders:");
        orders.forEach(o -> System.out.println("  " + o));

        double revenue = OrderAnalytics.completedRevenue(orders);
        System.out.printf("%nRevenue from COMPLETED orders: $%.2f%n", revenue);

        System.out.println("\nRevenue by category:");
        OrderAnalytics.revenueByCategory(orders).entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(e -> System.out.printf("  %-12s $%.2f%n", e.getKey(), e.getValue()));

        Map<Boolean, List<Order>> parts = OrderAnalytics.partitionByValue(orders, 500.0);
        System.out.println("\nHigh-value orders (>= $500): " + parts.get(true).size());
        parts.get(true).forEach(o -> System.out.println("  " + o));
        System.out.println("Regular orders: " + parts.get(false).size());
        parts.get(false).forEach(o -> System.out.println("  " + o));
    }
}
