package com.stns.crudapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "unique_category_name")})
public class Category {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @OneToMany(targetEntity = Product.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    @JsonManagedReference
    private List<Product> products;

    public Category(String name) {
        this.name = name;
    }
}
