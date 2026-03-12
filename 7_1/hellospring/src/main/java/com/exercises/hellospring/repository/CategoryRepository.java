package com.exercises.hellospring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercises.hellospring.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
