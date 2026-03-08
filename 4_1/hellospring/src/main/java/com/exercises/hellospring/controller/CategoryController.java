package com.exercises.hellospring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.exercises.hellospring.repository.CategoryRepository;
import com.exercises.hellospring.exception.ResourceNotFoundException;
import com.exercises.hellospring.model.Category;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    // not going to bother with DTO and ResponseEntity

    private CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Category getCategory(@PathVariable Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid Category id"));
    }

    @PostMapping
    public Category postMethodName(@RequestBody Category entity) {
        Category cat = categoryRepository.save(entity);
        return cat;
    }

    @PutMapping("/{id}")
    public Category putMethodName(@PathVariable Long id, @RequestBody Category entity) {
        Category cat = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid Category id"));
        cat.setName(entity.getName());
        cat.setDescription(entity.getDescription());

        categoryRepository.save(cat);
        return cat;
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
    }

}
