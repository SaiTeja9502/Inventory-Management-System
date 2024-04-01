package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class UserRequest {
	
    private String userID;
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit")
    private String password;
    @NotNull(message = "Role cannot be null")
    private String role;
    private boolean authorized = false;
    private String name;
    private String contact;
    private String address;

    // Constructors, getters, and setters...
    public UserRequest() {
    	
    }
    
    public UserRequest(String userID, String password, String role, boolean authorized, String name, String contact, String address) {
        this.userID = userID;
    	this.password = password;
        this.role = role;
        this.authorized = authorized;
        this.name = name;
        this.contact = contact;
        this.address = address;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
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


