package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;

@Document(collection = "products")
public class Product {

    @Id
    private String productID;
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Description cannot be null")
    private String description;
    @NotNull(message = "CategoryID cannot be null")
    private String categoryID;  // Assuming categoryID is a reference to another collection
    @NotNull(message = "URL cannot be null")
    private String imgURL;
    // Constructors, getters, and setters...

    public Product(String productID, String name, String description, String categoryID, String imgURL) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.categoryID = categoryID;
        this.imgURL = imgURL;
    }

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}
	
	public boolean equals(Object y)
	 {
	 if (y == this) 
		 return true;
	 if (y == null) 
		 return false;
	 if (y.getClass() != this.getClass())
		 return false;
	 Product that = (Product) y;
	 if (this.productID.compareTo(that.productID) != 0 ) return false;
	 if (this.name.compareTo(that.name) != 0) return false;
	 if (this.categoryID.compareTo(that.categoryID) !=0 ) return false;
	 if (this.description.compareTo(that.description) != 0) return false;
	 if (this.imgURL.compareTo(that.imgURL) != 0) return false;
	 return true;
	 }
}

