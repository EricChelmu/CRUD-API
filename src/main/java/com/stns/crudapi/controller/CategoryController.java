package com.stns.crudapi.controller;

import com.stns.crudapi.dto.CategoryRequest;
import com.stns.crudapi.dto.OrderResponse;
import com.stns.crudapi.entity.Category;
import com.stns.crudapi.repository.CategoryRepository;
import com.stns.crudapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Category placeInCategory(@RequestBody CategoryRequest request){
        return categoryRepository.save(request.getCategory());
    }

    @GetMapping("/category/all")
    public List<Category> findAllCategories(){
        return categoryRepository.findAll();
    }
}
