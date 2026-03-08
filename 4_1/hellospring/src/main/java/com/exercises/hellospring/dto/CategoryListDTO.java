package com.exercises.hellospring.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CategoryListDTO {
    Set<String> categories;

    public CategoryListDTO() {
        this.categories = new HashSet<>();
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public void addCategory(String category) {
        categories.add(category);
    }
}
