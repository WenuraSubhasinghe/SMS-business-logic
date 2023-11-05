package com.sms.businesslogic.service;

import com.sms.businesslogic.entity.Product;
import com.sms.businesslogic.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return optionalProduct.orElse(null);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Integer productId, Product updatedProduct) {
        if (productRepository.existsById(productId)) {
            updatedProduct.setProductId(productId);
            return productRepository.save(updatedProduct);
        }
        return null; // or throw an exception indicating the product doesn't exist
    }

    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }
}
