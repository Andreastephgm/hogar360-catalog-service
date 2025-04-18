package com.hogar360.catalog_service.domain.exceptions;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String name) {
        super("The category: '" + name + "' already exists.");
    }
}
