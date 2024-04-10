package com.stns.crudapi.repository;

import com.stns.crudapi.entity.Category;
import com.stns.crudapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    Product findByName(String name);

    List<Product> findByNameContainingIgnoreCase(String name);
}
