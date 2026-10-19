package com.streamsdemo.pipeline;

import com.streamsdemo.data.SampleData;
import com.streamsdemo.orders.Order;

import java.util.List;

/** Runnable demo: flattening nested order -> items with flatMap. */
public class PipelineDemo {
    public static void main(String[] args) {
        List<Order> orders = SampleData.orders();

        System.out.println("=== flatMap pipeline (Streams) ===");
        System.out.println("\nWithout flatMap, mapping orders to items gives");
        System.out.println("Stream<Stream<OrderItem>> -- a stream of streams. Not useful.");
        System.out.println("flatMap merges them into ONE flat Stream<OrderItem>.\n");

        System.out.println("Distinct products (all orders):");
        ProductPipeline.distinctProductsSorted(orders)
                .forEach(p -> System.out.println("  " + p));

        System.out.println("\nDistinct products (COMPLETED orders only):");
        ProductPipeline.distinctProductsSortedCompleted(orders)
                .forEach(p -> System.out.println("  " + p));
    }
}
