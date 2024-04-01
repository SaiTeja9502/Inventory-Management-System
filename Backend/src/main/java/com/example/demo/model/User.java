package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Document(collection = "users")
public class User {
	
    @Id
    @NotNull(message = "UserID cannot be null")
    private String userID;
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit")
    private String password;
    @NotNull(message = "Role cannot be null")
    private Role role;
    private boolean authorized = false;

    // Enum for user roles
    public enum Role {
        RETAILER,
        INVENTORY,
        SUPPLIER,
        ADMIN
    }

    // Constructors, getters, and setters...

    public User(String userID, String password, Role role, boolean authorized) {
        this.userID = userID;
    	this.password = password;
        this.role = role;
        this.authorized = authorized;
    }

    // Getters and setters...
    
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}
    
}

