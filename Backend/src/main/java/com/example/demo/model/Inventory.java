package com.example.demo.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Document(collection = "inventory")
public class Inventory {

    @Id
    @NotNull(message = "ProductID cannot be null")
    private String productID;
    @NotNull(message = "Suppliers cannot be null")
    private List<Supplier> suppliers;
    @NotNull(message = "Quantity cannot be null")
    @Min( value = 0, message = "Quantity cannot be less than zero")
    private int quantity;
    @NotNull(message = "Price cannot be null")
    @Min( value = 1, message = "Price cannot be zero or less than zero")
    private double price;
    @NotNull(message = "ReorderPoint cannot be null")
    @Min( value = 1, message = "Reorder Point cannot be less than zero or equal to zero")
    private int reorderpoint;
    @NotNull(message = "Units cannot be null")
    private String units;

    public Inventory() {}
    public Inventory(String productID, List<Supplier> suppliers, int quantity, double price, int reorderpoint, String units) {
        this.productID = productID;
        this.suppliers = suppliers;
        this.quantity = quantity;
        this.price = price;
        this.reorderpoint = reorderpoint;
        this.units = units;
    }

    // Getters and setters...

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getReorderpoint() {
        return reorderpoint;
    }

    public void setReorderpoint(int reorderpoint) {
        this.reorderpoint = reorderpoint;
    }

	public List<Supplier> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(List<Supplier> suppliers) {
		this.suppliers = suppliers;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}
}
