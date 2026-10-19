package com.streamsdemo.orders;

import java.util.List;

/** A customer order: header + line items. */
public class Order {
    private final String id;
    private final String customer;
    private final OrderStatus status;
    private final List<OrderItem> items;

    public Order(String id, String customer, OrderStatus status, List<OrderItem> items) {
        this.id = id;
        this.customer = customer;
        this.status = status;
        this.items = List.copyOf(items);
    }

    public String getId() { return id; }
    public String getCustomer() { return customer; }
    public OrderStatus getStatus() { return status; }
    public List<OrderItem> getItems() { return items; }

    /** Order total = sum of line totals. Streams make this a one-liner. */
    public double total() {
        return items.stream().mapToDouble(OrderItem::lineTotal).sum();
    }

    @Override
    public String toString() {
        return id + " [" + customer + ", " + status + "] $" + String.format("%.2f", total());
    }
}
