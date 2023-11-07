package com.sms.businesslogic.service;

import com.sms.businesslogic.dto.ProductDTO;
import com.sms.businesslogic.entity.Category;
import com.sms.businesslogic.entity.Product;
import com.sms.businesslogic.repository.CategoryRepository;
import com.sms.businesslogic.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;


    public ProductDTO productToProductDTO(Product product) {
        return ProductDTO.builder()
                .productName(product.getProductName())
                .quantity(product.getQuantity())
                .description(product.getDescription())
                .price(product.getPrice())
                .categoryId(product.getCategory().getCategoryId()) // Assuming category ID is part of Product entity
                .build();
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::productToProductDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Integer productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return optionalProduct.map(this::productToProductDTO).orElse(null);
    }

    public void createProduct(ProductDTO productDTO) {

        Category category= categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setQuantity(productDTO.getQuantity());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setCategory(category);
        productRepository.save(product);
    }

    public void updateProduct(Integer productId, ProductDTO productDTO) {

        Category category= categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Product product= productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (product!=null && category!=null) {
            product.setProductName(productDTO.getProductName());
            product.setQuantity(productDTO.getQuantity());
            product.setPrice(productDTO.getPrice());
            product.setDescription(productDTO.getDescription());
            product.setCategory(category);
            productRepository.save(product);

        }
    }

/*    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }*/

    public String deleteProduct(Integer productId) {
        try {
            productRepository.deleteById(productId);
            return "Delete successful";
        } catch (EmptyResultDataAccessException ex) {
            return "Product with ID " + productId + " does not exist";
        } catch (Exception e) {
            return "Error deleting product: " + e.getMessage();
        }
    }
}
