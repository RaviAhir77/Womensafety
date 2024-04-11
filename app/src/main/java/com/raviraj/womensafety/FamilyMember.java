package com.raviraj.womensafety;

public class FamilyMember {
    private String name;
    private String phone;
    private String email;
    private String userId; // Common identifier to link with User
    private String key; // Key in the Firebase database

    public FamilyMember() {
        // Default constructor required for Firebase
    }

    public FamilyMember(String name, String phone, String email, String userId) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.userId = userId;
    }

    // Getters and setters (required for Firebase)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    // Optional: Override toString() method for displaying in ListView or debugging
    @Override
    public String toString() {
        return "Name: " + name + "\nPhone: " + phone + "\nEmail: " + email;
    }
}
