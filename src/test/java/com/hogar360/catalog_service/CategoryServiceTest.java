package com.hogar360.catalog_service;

import com.hogar360.catalog_service.application.service.CategoryService;
import com.hogar360.catalog_service.domain.exceptions.CategoryAlreadyExistsException;
import com.hogar360.catalog_service.domain.model.Category;
import com.hogar360.catalog_service.infraestructure.persistence.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    private CategoryRepository repository;
    private CategoryService service;

    private static final String CATEGORY_NAME = "luxury";
    private static final String CATEGORY_ALREADY_EXISTS_MESSAGE = "The category  '" + CATEGORY_NAME + "' already exists.";

    @BeforeEach
    void setUp() {
        repository = mock(CategoryRepository.class);
        service = new CategoryService(repository);
    }

    @Test
    void createCategory_successfully() {
        // Arrange
        Category category = new Category(1L, CATEGORY_NAME);
        when(repository.findByName(CATEGORY_NAME)).thenReturn(Optional.empty());
        when(repository.save(any(Category.class))).thenReturn(category);

        Category result = service.createCategory(CATEGORY_NAME);

        assertThat(result.getName()).isEqualTo(CATEGORY_NAME);
        assertThat(result.getId()).isEqualTo(1L);
        verify(repository).save(any(Category.class));
    }

    @Test
    void createCategory_shouldThrowException_ifNameAlreadyExists() {
        // Arrange
        Category existingCategory = new Category(1L, CATEGORY_NAME);
        when(repository.findByName(CATEGORY_NAME)).thenReturn(Optional.of(existingCategory));

        CategoryAlreadyExistsException e = assertThrows(
                CategoryAlreadyExistsException.class,
                () -> service.createCategory(CATEGORY_NAME)
        );

        assertThat(e.getMessage()).isEqualTo(CATEGORY_ALREADY_EXISTS_MESSAGE);

        verify(repository, never()).save(any());
    }
}
