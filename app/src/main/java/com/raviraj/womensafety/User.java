package com.raviraj.womensafety;

public class User {
    private String username;
    private String phoneNumber;
    private String email;
    private String userId;

    // Constructors, getters, and setters

    public User() {
        // Default constructor required for Firebase
    }

    public User(String username, String phoneNumber, String email, String userId) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}

