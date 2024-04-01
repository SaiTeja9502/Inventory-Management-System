package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Document(collection = "quotations")
public class Quotation {

    @Id
    private String quotationID;
    @NotNull(message = "PurchaseID cannot be null")
    private String purchaseID;
    @NotNull(message = "SupplierID cannot be null")
    private String supplierID;
    @NotNull(message = "ProductID cannot be null")
    private String productID;
    @NotNull(message = "Price cannot be null")
    @PositiveOrZero(message = "Price must be non-negative")
    private double price;
    @NotNull
    private LocalDate localDate;
    
    @NotNull
    private LocalTime time;

    // Constructors, getters, and setters...

    public Quotation(String purchaseID, String supplierID, String productID, double price) {
        this.purchaseID = purchaseID;
        this.supplierID = supplierID;
        this.productID = productID;
        this.price = price;
        this.localDate = LocalDate.now();
        this.time = LocalTime.now();
    }

    // Getters and setters...
    
    public String getQuotationID() {
        return quotationID;
    }

    public void setQuotationID(String quotationID) {
        this.quotationID = quotationID;
    }

    public String getPurchaseID() {
        return purchaseID;
    }

    public void setPurchaseID(String purchaseID) {
        this.purchaseID = purchaseID;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

	public LocalDate getLocalDate() {
		return localDate;
	}

	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}
	
}

