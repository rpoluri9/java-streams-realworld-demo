package com.streamsdemo;

import com.streamsdemo.data.SampleData;
import com.streamsdemo.orders.Order;
import com.streamsdemo.orders.OrderAnalytics;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderAnalyticsTest {

    private final List<Order> orders = SampleData.orders();

    @Test
    void completedRevenueSumsOnlyCompletedOrders() {
        // 950 (O1) + 60 (O2) + 80 (O4) + 500 (O6) = 1590; PLACED/CANCELLED excluded
        assertEquals(1590.0, OrderAnalytics.completedRevenue(orders), 0.001);
    }

    @Test
    void revenueByCategory() {
        Map<String, Double> byCat = OrderAnalytics.revenueByCategory(orders);
        assertEquals(950.0, byCat.get("Electronics"), 0.001);
        assertEquals(90.0, byCat.get("Books"), 0.001);
        assertEquals(50.0, byCat.get("Stationery"), 0.001);
        assertEquals(500.0, byCat.get("Furniture"), 0.001);
        assertEquals(4, byCat.size());
    }

    @Test
    void partitionByValue() {
        Map<Boolean, List<Order>> parts = OrderAnalytics.partitionByValue(orders, 500.0);
        assertEquals(2, parts.get(true).size());   // O1 (950), O6 (500)
        assertEquals(2, parts.get(false).size());  // O2 (60), O4 (80)
        assertTrue(parts.get(true).stream().allMatch(o -> o.total() >= 500.0));
    }

    @Test
    void orderTotalIsSumOfLineItems() {
        Order o1 = orders.get(0);
        assertEquals(950.0, o1.total(), 0.001);
    }
}
