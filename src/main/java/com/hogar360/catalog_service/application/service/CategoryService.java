package com.hogar360.catalog_service.application.service;

import com.hogar360.catalog_service.domain.exceptions.CategoryAlreadyExistsException;
import com.hogar360.catalog_service.domain.model.Category;
import com.hogar360.catalog_service.infraestructure.persistence.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository repo;

    public CategoryService(CategoryRepository repo){
        this.repo = repo;
    }

    public Category createCategory(String name){
        repo.findByName(name)
                .ifPresent(cat -> {
                    throw new CategoryAlreadyExistsException(name);
                });

        return repo.save(new Category(null, name));
    }
}