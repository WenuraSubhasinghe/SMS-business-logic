package com.sms.businesslogic.controller;

import com.sms.businesslogic.dto.CategoryRequest;
import com.sms.businesslogic.entity.Category;
import com.sms.businesslogic.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/add")
    public ResponseEntity<String> addCategory(@RequestBody CategoryRequest categoryRequest) {
        Category category = categoryService.createCategory(categoryRequest.getCategoryName(), categoryRequest.getDescription());
        return ResponseEntity.ok("Category added successfully with ID : " + category.getCategoryId());
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.ok("Category deleted successfully");
    }

    @GetMapping("/categoryList")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PutMapping("/updateCategoryName/{categoryId}")
    public ResponseEntity<Category> updateCategoryName(@PathVariable Integer categoryId, @RequestParam String newCategoryName) {
        Category updateCategory = categoryService.updateCategoryName(categoryId, newCategoryName);

        if (updateCategory != null) {
            return  ResponseEntity.ok(updateCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
