package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;

@Document(collection = "retailers")
public class Retailer {

    @Id
    private String retailerID;
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Contact cannot be null")
    private String contact;
    private String address;
    // Constructors, getters, and setters...

    public Retailer() {
        // Default constructor
    }

    public Retailer(String retailerId, String name, String contact, String address) {
    	this.retailerID = retailerId;
        this.name = name;
        this.contact = contact;
        this.address = address;
    }

    // Getters and setters...
    
    public String getRetailerID() {
        return retailerID;
    }

    public void setRetailerID(String retailerID) {
        this.retailerID = retailerID;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}

