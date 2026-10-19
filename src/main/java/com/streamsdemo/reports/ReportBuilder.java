package com.streamsdemo.reports;

import com.streamsdemo.orders.Order;
import com.streamsdemo.orders.OrderItem;
import com.streamsdemo.orders.OrderStatus;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** Building human-readable reports: joining and toMap with a merge function. */
public final class ReportBuilder {

    private ReportBuilder() {}

    /** Comma-separated names, e.g. "Priya, Anil, Vikram". */
    public static String joinNames(List<String> names) {
        return names.stream().collect(Collectors.joining(", "));
    }

    /** Same, with prefix/suffix for a finished sentence. */
    public static String joinNamesFramed(List<String> names) {
        return names.stream().collect(Collectors.joining(", ", "Top earners: ", "."));
    }

    /**
     * Product -> total revenue across COMPLETED orders.
     * The merge function (Double::sum) is REQUIRED by toMap whenever two
     * elements could produce the same key -- here a product could appear in
     * several orders, so revenues must be added together, not overwritten.
     * Without a merge function, toMap throws IllegalStateException on duplicates.
     */
    public static Map<String, Double> productRevenue(List<Order> orders) {
        return orders.stream()
                .filter(o -> o.getStatus() == OrderStatus.COMPLETED)
                .flatMap(o -> o.getItems().stream())
                .collect(Collectors.toMap(
                        OrderItem::getProductName,
                        OrderItem::lineTotal,
                        Double::sum)); // merge duplicates by adding
    }
}
