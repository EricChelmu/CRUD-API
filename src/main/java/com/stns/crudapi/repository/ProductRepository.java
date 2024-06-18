package com.stns.crudapi.repository;

import com.stns.crudapi.entity.Category;
import com.stns.crudapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    Product findByName(String name);

    @Query("SELECT p FROM Product p WHERE p.image.id = :imageId")
    List<Product> findByImageId(@Param("imageId") Integer imageId);

    @Query("SELECT p FROM Product p WHERE (:categoryId IS NULL OR p.category.id = :categoryId) AND (:minPrice IS NULL OR p.price >= :minPrice) AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Product> findByCategoryAndPriceRange(
            @Param("categoryId") Integer categoryId,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice);
}
