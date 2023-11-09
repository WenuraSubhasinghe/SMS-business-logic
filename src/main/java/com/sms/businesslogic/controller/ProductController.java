package com.sms.businesslogic.controller;

import com.sms.businesslogic.dto.ProductDTO;
import com.sms.businesslogic.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts() {

        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer productId) {
        ProductDTO productDTO = productService.getProductById(productId);
        if (productDTO != null) {
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
       try {
           productService.createProduct(productDTO);
           return  ResponseEntity.ok("Product saved successfully");
       }
       catch (Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("Error saving the product: "+e.getMessage());
       }
    }

  @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer productId, @RequestBody ProductDTO productDTO) {
      try {
          productService.updateProduct(productId,productDTO);
          return  ResponseEntity.ok("Product updatd successfully");
      }
      catch (Exception e){
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                  .body("Error saving the product: "+e.getMessage());
      }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer productId) {
        String deletionMessage = productService.deleteProduct(productId);

        if ("Delete successful".equals(deletionMessage)) {
            return new ResponseEntity<>(deletionMessage, HttpStatus.OK);
        } else if (deletionMessage.startsWith("Product with ID")) {
            return new ResponseEntity<>(deletionMessage, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(deletionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
