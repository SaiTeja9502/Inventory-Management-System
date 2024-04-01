package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Document(collection = "orders")
public class Order {

    @Id
    private String orderID;
    @NotNull(message = "RetailerID cannot be null")
    private String retailerID;
    private LocalDate date;
    
    private double amount;
    private String productId;  // Represents a list of products in the order
    private int quantity;
    // Constructors, getters, and setters...

    public Order(String retailerID, double amount, String productId, int quantity) {
        this.retailerID = retailerID;
        this.date = LocalDate.now();
        this.amount = amount;
        this.productId = productId;
        this.quantity = quantity;
    }

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getRetailerID() {
		return retailerID;
	}

	public void setRetailerID(String retailerID) {
		this.retailerID = retailerID;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}

