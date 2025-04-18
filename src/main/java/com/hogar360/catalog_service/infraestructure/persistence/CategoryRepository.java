package com.hogar360.catalog_service.infraestructure.persistence;

import com.hogar360.catalog_service.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
