package com.openclassrooms.rentals.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

public class RentalDTO {
    private Long id;
    private String name;
    private double surface;
    private double price;
    private String picture;
    private MultipartFile newPicture;
    private String description;
    private Long ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSurface() {
        return surface;
    }

    public double getPrice() {
        return price;
    }

    public String getPicture() {
        return picture;
    }

    public MultipartFile getNewPicture() {
        return newPicture;
    }

    public String getDescription() {
        return description;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setNewPicture(MultipartFile newPicture) {
        this.newPicture = newPicture;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}