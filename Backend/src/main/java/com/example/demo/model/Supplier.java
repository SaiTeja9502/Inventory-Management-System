package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;

import java.util.List;

@Document(collection = "suppliers")
public class Supplier {

    @Id
    private String supplierID;
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Contact cannot be null")
    private String contact;
    private List<Product> products;  // Assuming Product is another class representing product information
    private String address;
    
    // Constructors, getters, and setters...

    public Supplier() {
        // Default constructor
    }

    public Supplier(String supplierId, String name, String contact, List<Product> products, String address) {
        this.supplierID = supplierId;
    	this.name = name;
        this.contact = contact;
        this.products = products;
        this.address = address;
    }

	public String getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(String supplierID) {
		this.supplierID = supplierID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}

