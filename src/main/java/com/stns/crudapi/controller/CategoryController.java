package com.stns.crudapi.controller;

import com.stns.crudapi.dto.CategoryRequest;
import com.stns.crudapi.dto.CategoryResponse;
import com.stns.crudapi.dto.OrderResponse;
import com.stns.crudapi.entity.Category;
import com.stns.crudapi.repository.CategoryRepository;
import com.stns.crudapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public Page<CategoryResponse> findAllCategories(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "12") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoriesPage = categoryRepository.findAll(pageable);

        List<CategoryResponse> categoryResponses = categoriesPage.getContent().stream().map(category -> {
            CategoryResponse response = new CategoryResponse();
            response.setId(category.getId());
            response.setName(category.getName());
            response.setDescription(category.getDescription());
            response.setProductCount(category.getProducts().size());
            return response;
        }).collect(Collectors.toList());

        return new PageImpl<>(categoryResponses, pageable, categoriesPage.getTotalElements());
    }
}
