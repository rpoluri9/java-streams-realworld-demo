package com.streamsdemo.pipeline;

import com.streamsdemo.orders.Order;
import com.streamsdemo.orders.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

/**
 * flatMap: the star of nested collections.
 *
 * map() turns one element into ONE value.
 * flatMap() turns one element into a STREAM of values and merges
 * ("flattens") all those streams into a single stream.
 *
 * Classic case: Order -> Stream&lt;OrderItem&gt;. Without flatMap you get
 * Stream&lt;Stream&lt;OrderItem&gt;&gt; (a stream of streams - useless).
 * With flatMap you get one flat Stream&lt;OrderItem&gt; to keep working on.
 */
public final class ProductPipeline {

    private ProductPipeline() {}

    /** Distinct product names across ALL orders, alphabetically sorted. */
    public static List<String> distinctProductsSorted(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.getItems().stream()) // Stream<Order> -> Stream<OrderItem>
                .map(OrderItem::getProductName)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    /** Same, but only for COMPLETED orders. */
    public static List<String> distinctProductsSortedCompleted(List<Order> orders) {
        return orders.stream()
                .filter(o -> o.getStatus() == com.streamsdemo.orders.OrderStatus.COMPLETED)
                .flatMap(o -> o.getItems().stream())
                .map(OrderItem::getProductName)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
