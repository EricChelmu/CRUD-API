package com.stns.crudapi.controller;

import com.stns.crudapi.dto.CategoryRequest;
import com.stns.crudapi.dto.OrderResponse;
import com.stns.crudapi.entity.Category;
import com.stns.crudapi.repository.CategoryRepository;
import com.stns.crudapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/category")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> placeInCategory(@RequestBody @Validated CategoryRequest request){
        try {
            if (categoryRepository.existsByName(request.getCategory().getName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Category with the same name already exists");
            }
        Category savedCategory = categoryRepository.save(request.getCategory());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @GetMapping("/category/all")
    public List<Category> findAllCategories(){
        return categoryRepository.findAll();
    }
}
