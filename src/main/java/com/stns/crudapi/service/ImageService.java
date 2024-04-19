package com.stns.crudapi.service;

import com.stns.crudapi.entity.Image;
import com.stns.crudapi.entity.Product;
import com.stns.crudapi.repository.ImageRepository;
import com.stns.crudapi.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Value("${image.baseDir}")
    private String baseDir;

    public String uploadImageToFileSystem(MultipartFile file) throws IOException {

        String filePath = baseDir + file.getOriginalFilename();

        Image image = imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .path(filePath).build());

        file.transferTo(new File(filePath));

        if (filePath != null) {
            return "file uploaded succesfully: " + filePath;
        }
        return null;

    }

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<Image> fileData = imageRepository.findByName(fileName);
        String filePath = fileData.get().getPath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }

    public void deleteImageById(Integer id) {
        Optional<Image> optionalImage = imageRepository.findById(id);
        if (optionalImage.isPresent()) {
            Image image = optionalImage.get();

            // Find products referencing this image
            List<Product> products = productRepository.findByImageId(id);

            // Remove references to the image in products
            for (Product product : products) {
                product.setImage(null); // Assuming you have a setImage method in your Product entity
                productRepository.save(product);
            }

            // Now you can safely delete the image
            imageRepository.delete(image);
        } else {
            throw new EntityNotFoundException("Image not found with id: " + id);
        }
    }
}
