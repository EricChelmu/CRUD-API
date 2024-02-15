package com.stns.crudapi.controller;

import com.stns.crudapi.dto.CategoryRequest;
import com.stns.crudapi.dto.OrderResponse;
import com.stns.crudapi.entity.Category;
import com.stns.crudapi.entity.Product;
import com.stns.crudapi.repository.CategoryRepository;
import com.stns.crudapi.repository.ProductRepository;
import com.stns.crudapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    @Autowired
    private ProductService service;
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/product")
    public Product addProduct(@RequestBody Product product){
        return service.saveProduct(product);
    }

    @PostMapping("/product/all")
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
    public Product updateProduct(@RequestBody Product product){
        return service.updateProduct(product);
    }

    @DeleteMapping("/product/{id}")
    public String deleteProduct(@PathVariable int id){
        return service.deleteProduct(id);
    }
}
