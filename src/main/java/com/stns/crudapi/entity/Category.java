package com.stns.crudapi.entity;

import jakarta.persistence.*;
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
    private String name;
    @OneToMany(targetEntity = Product.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private List<Product> products;
}
