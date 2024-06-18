package com.stns.crudapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private int id;
    @NotNull
    @NotBlank
    private String name;
    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;
    @Min(value = 0, message = "Price cannot be negative")
    private double price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("products")
    @JsonBackReference
    private Category category;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    @JsonIgnore
    private Image image;

    public Product(String name, int quantity, double price, Category category) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = Math.round(price * 100.0) / 100.0;
    }

}
