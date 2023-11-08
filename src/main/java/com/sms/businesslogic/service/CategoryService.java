package com.sms.businesslogic.service;

import com.sms.businesslogic.entity.Category;
import com.sms.businesslogic.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(String categoryName,String description){
        Category newCategory = new Category();
        newCategory.setCategoryName(categoryName);
        newCategory.setDescription(description);

        return categoryRepository.save(newCategory);
    }
    public void deleteCategoryById(Integer categoryId) {
        categoryRepository.deleteById(categoryId);
    }
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
    public Category updateCategoryName(Integer categoryId, String newCategoryName) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setCategoryName(newCategoryName);
            return categoryRepository.save(category);
        } else {
            return null;
        }
    }
}
