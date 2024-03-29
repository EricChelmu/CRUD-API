package com.stns.crudapi.controller;

import com.stns.crudapi.dto.OrderResponse;
import com.stns.crudapi.entity.Product;
import com.stns.crudapi.repository.CategoryRepository;
import com.stns.crudapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@Validated
public class ProductController {

    @Autowired
    private ProductService service;
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/product")
    @PreAuthorize("hasAuthority('admin')")
    public Product addProduct(@RequestBody Product product){
        return service.saveProduct(product);
    }

    @PostMapping("/product/all")
    @PreAuthorize("hasAuthority('admin')")
    public List<Product> addProducts(@RequestBody List<Product> products){
        return service.saveProducts(products);
    }

    @GetMapping("/product/all")
    public List<OrderResponse> getJoinInformation() {
        return categoryRepository.getJoinInformation();
    }

    @GetMapping("/product/{id}")
    public Object findProductById(@PathVariable int id){
        return service.getProductById(id);
    }

    @GetMapping("/productByName/{name}")
    public Product findProductByName(@PathVariable String name){
        return service.getProductByName(name);
    }

    @PutMapping("/product")
    @PreAuthorize("hasAuthority('admin')")
    public Product updateProduct(@RequestBody Product product){
        return service.updateProduct(product);
    }

    @DeleteMapping("/product/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public String deleteProduct(@PathVariable int id){
        return service.deleteProduct(id);
    }

}
