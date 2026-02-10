package com.mirkamolcode;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Product {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Integer stockLevel;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
    private Boolean isPublished;

    public Product(UUID id, String name, String description, BigDecimal price, String imageUrl, Integer stockLevel, Instant createdAt, Instant updatedAt, Instant deletedAt, Boolean isPublished) {
        this.id = (id == null) ? UUID.randomUUID() : id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.stockLevel = stockLevel;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.isPublished = isPublished;
    }

    public Product(UUID id, String name, String description, BigDecimal price, String imageUrl, Integer stockLevel, Instant createdAt) {
        this.id = (id == null) ? UUID.randomUUID() : id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.stockLevel = stockLevel;
        this.createdAt = createdAt;
    }


    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(Integer stockLevel) {
        this.stockLevel = stockLevel;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", stockLevel=" + stockLevel +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                ", isPublished=" + isPublished +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!id.equals(product.id)) return false;
        if (!name.equals(product.name)) return false;
        if (!description.equals(product.description)) return false;
        if (!price.equals(product.price)) return false;
        if (!imageUrl.equals(product.imageUrl)) return false;
        if (!stockLevel.equals(product.stockLevel)) return false;
        if (!createdAt.equals(product.createdAt)) return false;
        if (!updatedAt.equals(product.updatedAt)) return false;
        if (deletedAt != null ? !deletedAt.equals(product.deletedAt) : product.deletedAt != null) return false;
        return isPublished.equals(product.isPublished);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + imageUrl.hashCode();
        result = 31 * result + stockLevel.hashCode();
        result = 31 * result + createdAt.hashCode();
        result = 31 * result + updatedAt.hashCode();
        result = 31 * result + (deletedAt != null ? deletedAt.hashCode() : 0);
        result = 31 * result + isPublished.hashCode();
        return result;
    }
}
