package com.stns.crudapi.service;

import com.stns.crudapi.dto.OrderResponse;
import com.stns.crudapi.entity.Image;
import com.stns.crudapi.entity.Product;
import com.stns.crudapi.repository.ImageRepository;
import com.stns.crudapi.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ImageRepository imageRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${image.baseDir}")
    private String baseDir;

    public Product saveProduct(Product product){
        return repository.save(product);
    }

    public Product saveProductWithImage(Product product, MultipartFile file) throws IOException {



        Image image = saveImage(file);

        product.setImage(image);

        return repository.save(product);
    }

    public OrderResponse getProductByIdWithImage(int id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(product.getId());
        orderResponse.setName(product.getName());
        orderResponse.setCategory(product.getCategory().getName());
        orderResponse.setQuantity(product.getQuantity());
        orderResponse.setPrice(product.getPrice());

        if (product.getImage() != null) {
            orderResponse.setImagePath(product.getImage().getPath());
        } else {
            // Assign a default image that already exists in the database
            Image defaultImage = imageRepository.findById(2)
                    .orElseThrow(() -> new EntityNotFoundException("Default Image not found"));

            // Update the product's image with the default image
            product.setImage(defaultImage);
            repository.save(product);

            orderResponse.setImagePath(defaultImage.getPath());
        }

        return orderResponse;
    }

    private Image saveImage(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String filePath = Paths.get(baseDir, fileName).toString();
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        // Instead of directly saving the image to the repository,
        // create an Image entity and return it for further processing
        Image image = new Image();
        image.setName(fileName);
        image.setType(file.getContentType());
        image.setPath(filePath);

        return imageRepository.save(image);
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
        return jsonMessage;
    }

    public Product getProductByName(String name){
        return repository.findByName(name);
    }

    public String deleteProduct(int id){
        try {
            repository.deleteById(id);
            return "Product deleted successfully";
        } catch (Exception e) {
            return "Error deleting product: " + e.getMessage();
        }
    }

    public List<Product> searchProductsByName(String name) {
        String queryString = "SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(:name)";
        Query query = entityManager.createQuery(queryString, Product.class);
        query.setParameter("name", "%" + name + "%");

        return query.getResultList();
    }

    public Product updateProduct(Product product){
        Product existingProduct = repository.findById(product.getId()).orElse(null);
        existingProduct.setName(product.getName());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setPrice(product.getPrice());
        return repository.save(existingProduct);
    }

    public Product assignImageToProduct(int productId, int imageId) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Image not found"));

        product.setImage(image);
        return repository.save(product);
    }
}
