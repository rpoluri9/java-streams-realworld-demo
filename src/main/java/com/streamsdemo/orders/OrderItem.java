package com.streamsdemo.orders;

/** One line item inside an order. */
public class OrderItem {
    private final String productName;
    private final String category;
    private final double price;
    private final int quantity;

    public OrderItem(String productName, String category, double price, int quantity) {
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductName() { return productName; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    /** Revenue contributed by this line item. */
    public double lineTotal() { return price * quantity; }

    @Override
    public String toString() {
        return productName + " x" + quantity + " = $" + String.format("%.2f", lineTotal());
    }
}
