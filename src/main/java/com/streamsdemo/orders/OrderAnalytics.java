package com.streamsdemo.orders;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** Real-world stream analytics over orders. */
public final class OrderAnalytics {

    private OrderAnalytics() {}

    /** Total revenue from COMPLETED orders only: filter -> map -> reduce(sum). */
    public static double completedRevenue(List<Order> orders) {
        return orders.stream()
                .filter(o -> o.getStatus() == OrderStatus.COMPLETED)
                .mapToDouble(Order::total)
                .sum();
    }

    /** Revenue per product category across COMPLETED orders. */
    public static Map<String, Double> revenueByCategory(List<Order> orders) {
        return orders.stream()
                .filter(o -> o.getStatus() == OrderStatus.COMPLETED)
                .flatMap(o -> o.getItems().stream())
                .collect(Collectors.groupingBy(
                        OrderItem::getCategory,
                        Collectors.summingDouble(OrderItem::lineTotal)));
    }

    /** Split COMPLETED orders into high-value (>= threshold) vs regular. */
    public static Map<Boolean, List<Order>> partitionByValue(List<Order> orders, double threshold) {
        return orders.stream()
                .filter(o -> o.getStatus() == OrderStatus.COMPLETED)
                .collect(Collectors.partitioningBy(o -> o.total() >= threshold));
    }
}
