package com.streamsdemo.data;

import com.streamsdemo.orders.Order;
import com.streamsdemo.orders.OrderItem;
import com.streamsdemo.orders.OrderStatus;

import java.util.List;

/** Small realistic datasets shared by demos and tests. */
public final class SampleData {

    private SampleData() {}

    public static List<Order> orders() {
        return List.of(
            new Order("O1", "Alice", OrderStatus.COMPLETED, List.of(
                new OrderItem("Laptop", "Electronics", 900.00, 1),
                new OrderItem("Mouse", "Electronics", 25.00, 2))),
            new Order("O2", "Bob", OrderStatus.COMPLETED, List.of(
                new OrderItem("Book", "Books", 20.00, 3))),
            new Order("O3", "Alice", OrderStatus.PLACED, List.of(
                new OrderItem("Keyboard", "Electronics", 75.00, 1))),
            new Order("O4", "Carol", OrderStatus.COMPLETED, List.of(
                new OrderItem("Novel", "Books", 15.00, 2),
                new OrderItem("Pen", "Stationery", 5.00, 10))),
            new Order("O5", "Dave", OrderStatus.CANCELLED, List.of(
                new OrderItem("Monitor", "Electronics", 300.00, 1))),
            new Order("O6", "Eve", OrderStatus.COMPLETED, List.of(
                new OrderItem("Chair", "Furniture", 250.00, 2)))
        );
    }

    public static List<com.streamsdemo.hr.Employee> employees() {
        return List.of(
            new com.streamsdemo.hr.Employee("Anil", "Engineering", 120000),
            new com.streamsdemo.hr.Employee("Priya", "Engineering", 135000),
            new com.streamsdemo.hr.Employee("Kiran", "Engineering", 95000),
            new com.streamsdemo.hr.Employee("Vikram", "Engineering", 110000),
            new com.streamsdemo.hr.Employee("Meena", "Sales", 80000),
            new com.streamsdemo.hr.Employee("Ravi", "Sales", 70000),
            new com.streamsdemo.hr.Employee("Sara", "HR", 65000),
            new com.streamsdemo.hr.Employee("Uma", "HR", 90000)
        );
    }

    public static List<String> logLines() {
        return List.of(
            "2026-10-19T09:05:12 INFO  Application started",
            "2026-10-19T09:15:30 ERROR Database connection failed",
            "2026-10-19T09:16:02 WARN  Retry attempt 1 for database",
            "2026-10-19T09:17:44 ERROR Database connection failed",
            "2026-10-19T10:02:10 INFO  User alice logged in",
            "2026-10-19T10:20:55 ERROR Timeout while calling payment API",
            "2026-10-19T10:31:00 INFO  Order O1 completed",
            "2026-10-19T11:00:12 DEBUG Cache miss for product Laptop",
            "2026-10-19T11:05:40 ERROR Database connection failed",
            "2026-10-19T11:42:21 INFO  Daily report generated"
        );
    }
}
