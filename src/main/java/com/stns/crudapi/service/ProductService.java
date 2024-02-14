package com.stns.crudapi.service;

import com.stns.crudapi.entity.Product;
import com.stns.crudapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product saveProduct(Product product){
        return repository.save(product);
    }

    public List<Product> saveProducts(List<Product> products){
        return repository.saveAll(products);
    }

    public List<Product> getProducts(){
        return repository.findAll();
    }

    public Object getProductById(int id){
        Product product = repository.findById(id).orElse(null);
        if (product != null) {
            return product;
        } else {
            // If no product is found, create a placeholder product or handle the case accordingly
            return createNotFoundJsonMessage(id);
        }
    }

    private Map<String, String> createNotFoundJsonMessage(int id) {
        Map<String, String> jsonMessage = new HashMap<>();
        jsonMessage.put("error", "Product not found");
        jsonMessage.put("id", String.valueOf(id));
        // Add more properties as needed

        return jsonMessage;
    }

    public Product getProductByName(String name){
        return repository.findByName(name);
    }

    public String deleteProduct(int id){
        repository.deleteById(id);
        return "product removed!! "+id;
    }

    public Product updateProduct(Product product){
        Product existingProduct = repository.findById(product.getId()).orElse(null);
        existingProduct.setName(product.getName());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setPrice(product.getPrice());
        return repository.save(existingProduct);
    }
}
