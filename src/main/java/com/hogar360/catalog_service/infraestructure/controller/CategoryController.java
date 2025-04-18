package com.hogar360.catalog_service.infraestructure.controller;

import com.hogar360.catalog_service.application.service.CategoryService;
import com.hogar360.catalog_service.domain.exceptions.CategoryAlreadyExistsException;
import com.hogar360.catalog_service.domain.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/categories")

public class CategoryController {
    private final CategoryService svc;
    public CategoryController(CategoryService svc){
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Map<String, String> body) {
        String name = body.get("name");

        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("The 'name' field is required and cannot be empty.");
        }
        try {
            Category cat = svc.createCategory(name);
            return ResponseEntity.status(201).body(cat);
        } catch (CategoryAlreadyExistsException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<Page<Category>> listCategories(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "filter", defaultValue = " ") String filter

    ){
        Page<Category> categories = svc.listCategories(PageRequest.of(page, size), filter);
        return ResponseEntity.ok(categories);
    }
}
