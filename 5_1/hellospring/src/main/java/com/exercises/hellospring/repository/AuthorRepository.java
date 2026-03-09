package com.exercises.hellospring.repository;

import com.exercises.hellospring.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    
}
