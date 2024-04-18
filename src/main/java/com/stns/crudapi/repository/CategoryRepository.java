package com.stns.crudapi.repository;

import com.stns.crudapi.dto.OrderResponse;
import com.stns.crudapi.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query("SELECT NEW com.stns.crudapi.dto.OrderResponse(c.name, p.name, p.quantity, p.price, p.id, " +
            "COALESCE(p.image.path, '')) " +
            "FROM Category c JOIN c.products p LEFT JOIN p.image")
    public Page<OrderResponse> getJoinInformation(Pageable pageable);

    Optional<Category> findByName(String name);

    boolean existsByName(String name);
}
