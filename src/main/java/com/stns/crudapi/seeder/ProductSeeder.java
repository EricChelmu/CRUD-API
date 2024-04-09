package com.stns.crudapi.seeder;

import com.stns.crudapi.entity.Category;
import com.stns.crudapi.entity.Product;
import com.stns.crudapi.repository.CategoryRepository;
import com.stns.crudapi.repository.ProductRepository;
import com.stns.crudapi.service.ProductService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class ProductSeeder {

    @Autowired
    private ProductService service;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostConstruct
    public void seedProducts() {
        List<Product> existingProducts = productRepository.findAll();
        if (existingProducts.isEmpty()) {
            seedInitialProducts();
            System.out.println("Products seeded successfully.");
        } else {
            System.out.println("Products seeding not required.");
        }
    }

    private void seedInitialProducts() {
        List<Category> categories = generateCategories(10);
        categoryRepository.saveAll(categories);

        List<Category> savedCategories = categoryRepository.findAll();

        List<Product> products = generateProducts(10000, savedCategories);
            service.saveProducts(products);

    }

    private List<Category> generateCategories(int count) {
        List<Category> categories = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            String name = "Category " + i;
            Category category = new Category(name);
            categories.add(category);
        }
        return categories;
    }

    private List<Product> generateProducts(int count, List<Category> categories) {
        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            String name = "Product " + i;
            int quantity = (int) (Math.random() * 100) + 1;
            double price = (Math.random() * 1000) + 1;
            Category category = categories.get(ThreadLocalRandom.current().nextInt(categories.size()));
            Product product = new Product(name, quantity, price, category);
            products.add(product);
        }
        return products;
    }
}
