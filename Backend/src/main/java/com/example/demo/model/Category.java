package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;

@Document(collection = "categories")
public class Category {

    @Id
    private String categoryID;
    @NotNull(message = "Name cannot be null")
    private String name;

    // Constructors, getters, and setters..
    public Category(String categoryID, String name) {
    	this.categoryID = categoryID;
    	this.name = name;
    }

	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

