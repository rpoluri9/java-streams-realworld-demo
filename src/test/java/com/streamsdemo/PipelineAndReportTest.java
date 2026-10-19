package com.streamsdemo;

import com.streamsdemo.data.SampleData;
import com.streamsdemo.orders.Order;
import com.streamsdemo.pipeline.ProductPipeline;
import com.streamsdemo.reports.ReportBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PipelineAndReportTest {

    private final List<Order> orders = SampleData.orders();

    @Test
    void distinctProductsSortedAllOrders() {
        assertEquals(
                List.of("Book", "Chair", "Keyboard", "Laptop", "Monitor", "Mouse", "Novel", "Pen"),
                ProductPipeline.distinctProductsSorted(orders));
    }

    @Test
    void distinctProductsSortedCompletedOnly() {
        // Keyboard (PLACED order) and Monitor (CANCELLED order) are excluded
        assertEquals(
                List.of("Book", "Chair", "Laptop", "Mouse", "Novel", "Pen"),
                ProductPipeline.distinctProductsSortedCompleted(orders));
    }

    @Test
    void joiningNames() {
        assertEquals("Priya, Anil, Vikram",
                ReportBuilder.joinNames(List.of("Priya", "Anil", "Vikram")));
        assertEquals("Top earners: Priya, Anil, Vikram.",
                ReportBuilder.joinNamesFramed(List.of("Priya", "Anil", "Vikram")));
    }

    @Test
    void productRevenueMergesAcrossOrders() {
        Map<String, Double> revenue = ReportBuilder.productRevenue(orders);
        assertEquals(900.0, revenue.get("Laptop"), 0.001);
        assertEquals(50.0, revenue.get("Mouse"), 0.001);
        assertEquals(60.0, revenue.get("Book"), 0.001);
        assertEquals(30.0, revenue.get("Novel"), 0.001);
        assertEquals(50.0, revenue.get("Pen"), 0.001);
        assertEquals(500.0, revenue.get("Chair"), 0.001);
        assertEquals(6, revenue.size());
    }
}
