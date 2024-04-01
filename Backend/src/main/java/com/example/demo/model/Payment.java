package com.example.demo.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;

@Document(collection = "payments")
public class Payment {

    @Id
    private String paymentID;
    @NotNull(message = "OrderID cannot be null")
    private String orderID;
    @NotNull(message = "Amount cannot be null")
    private double amount;
    private LocalDate date;
    @NotNull(message = "Method cannot be null")
    private String method;
    private Role role;
    
    private enum Role {
        RETAILER,
        INVENTORY
    }

    // Constructors, getters, and setters...
    public Payment(String orderID, double amount, String method, String role) {
        this.orderID = orderID;
        this.amount = amount;
        this.date = LocalDate.now();
        this.method = method;
        this.role = Role.valueOf(role);
    }

    // Getters and setters...
    
    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}

