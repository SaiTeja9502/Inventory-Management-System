package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;

@Document(collection = "messages")
public class Message {
	@Id
	private String messageId;
	@NotNull
    private String message;
	@NotNull
    private UserType user;
	@NotNull
    private String userId;
	@NotNull
    private boolean isRead;

    // Constructors, getters, and setters...

    public enum UserType {
        INVENTORY,
        SUPPLIER
        // Add more user types as needed
    }

    public Message(String message, UserType user, String userId) {
        this.message = message;
        this.user = user;
        this.userId = userId;
        this.isRead = true; // Default to true
    }

    // Getters and setters...

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserType getUser() {
        return user;
    }

    public void setUser(UserType user) {
        this.user = user;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public boolean equals(Object y)
	 {
	 if (y == this) 
		 return true;
	 if (y == null) 
		 return false;
	 if (y.getClass() != this.getClass())
		 return false;
	 Message that = (Message) y;
	 if (this.message != that.message ) return false;
	 if (this.user.compareTo(that.user) != 0) return false;
	 if (this.userId != that.userId ) return false;
	 if( this.isRead != that.isRead) return false;
	 return true;
	 }
}

