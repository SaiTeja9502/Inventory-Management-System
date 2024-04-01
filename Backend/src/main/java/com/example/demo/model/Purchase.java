package com.example.demo.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Document(collection = "purchases")
public class Purchase {

    @Id
    private String purchaseID;
    @NotNull(message = "ProductID cannot be null")
    private String productID;
    @NotNull(message = "Status cannot be null")
    private Status status;
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity should be atleast one")
    private int quantity;
    private LocalDate date;
    private Double acceptedPrice; // Use Double instead of double to allow null

    // Enum for status values
    public enum Status {
        INITIATED,
        QUOTATIONS_AVAILABLE,
        ACCEPTED
    }

    // Constructors, getters, and setters...

    public Purchase(String productID, Status status, int quantity) {
        this.productID = productID;
        this.status = status;
        this.quantity = quantity;
        this.date = LocalDate.now();
        // acceptedPrice will be initialized to null by default
    }

    // Getters and setters...
    
    public String getPurchaseID() {
        return purchaseID;
    }

    public void setPurchaseID(String purchaseID) {
        this.purchaseID = purchaseID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Double getAcceptedPrice() {
        return acceptedPrice;
    }

    public void setAcceptedPrice(Double acceptedPrice) {
        this.acceptedPrice = acceptedPrice;
    }
}

