package com.stns.crudapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stns.crudapi.dto.OrderResponse;
import com.stns.crudapi.entity.Product;
import com.stns.crudapi.repository.CategoryRepository;
import com.stns.crudapi.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Validated
public class ProductController {

    @Autowired
    private ProductService service;
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/product")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> addProduct(@RequestParam("jsonData") String jsonData,
                                        @RequestPart("image")MultipartFile image){
        try {
            Product product = convertJsonToProduct(jsonData);

            Product savedProduct = service.saveProductWithImage(product, image);

            return ResponseEntity.ok(savedProduct);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body
                    ("Failed to save product with image: " + e.getMessage());
        }
    }

    private Product convertJsonToProduct(String jsonData) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonData, Product.class);
    }

    @PostMapping("/product/all")
    @PreAuthorize("hasAuthority('admin')")
    public List<Product> addProducts(@RequestBody List<Product> products) {
        return service.saveProducts(products);
    }

    @GetMapping("/product/all")
    public Page<OrderResponse> getJoinInformation(@RequestParam(defaultValue = "1") int page,
                                                  @RequestParam(defaultValue = "12") int size) {

        int adjustedPage = page - 1;

        Pageable pageable = PageRequest.of(adjustedPage, size);
        return categoryRepository.getJoinInformation(pageable);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> findProductById(@PathVariable int id){
        try {
            OrderResponse productWithImage = service.getProductByIdWithImage(id);
            return ResponseEntity.ok(productWithImage);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found: " + e.getMessage());
        }
    }

    @GetMapping("/productByName/{name}")
    public Product findProductByName(@PathVariable String name){
        return service.getProductByName(name);
    }

    @GetMapping("/product/search")
    public List<OrderResponse> searchProductsByName(@RequestParam String name) {
        List<OrderResponse> orderResponses = service.searchProductsByName(name);

        if (orderResponses == null) {
            return new ArrayList<>();
        }

        return orderResponses;
    }

    @GetMapping("/product/filter")
    public ResponseEntity<List<Product>> filterProducts(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        List<Product> filteredProducts = service.filterProducts(categoryId, minPrice, maxPrice);
        return ResponseEntity.ok(filteredProducts);
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

    @PutMapping("/product/{productId}/image/{imageId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> assignImageToProduct(@PathVariable int productId, @PathVariable int imageId) {
        try {
            Product updatedProduct = service.assignImageToProduct(productId, imageId);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to assign image to product: " + e.getMessage());
        }
    }

}
