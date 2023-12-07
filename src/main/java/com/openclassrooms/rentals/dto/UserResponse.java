package com.openclassrooms.rentals.dto;

public class UserResponse {
    private String email;
    private String name;

    // Constructeur par défaut
    public UserResponse() {
    }

    // Constructeur avec paramètres
    public UserResponse(String email, String name) {
        this.email = email;
        this.name = name;
    }

    // Getters et setters
    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
}