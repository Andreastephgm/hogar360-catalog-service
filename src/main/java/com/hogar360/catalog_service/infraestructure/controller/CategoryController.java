package com.hogar360.catalog_service.infraestructure.controller;

import com.hogar360.catalog_service.application.service.CategoryService;
import com.hogar360.catalog_service.domain.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/categories")

public class CategoryController {
    private final CategoryService svc;
    public CategoryController(CategoryService svc){
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<Category>  create(@RequestBody Map<String, String> body){
        Category cat = svc.createCategory(body.get("name"));
        return ResponseEntity.status(201).body(cat);
    }
}
