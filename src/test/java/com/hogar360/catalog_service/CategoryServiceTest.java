package com.hogar360.catalog_service;

import com.hogar360.catalog_service.application.service.CategoryService;
import com.hogar360.catalog_service.domain.exceptions.CategoryAlreadyExistsException;
import com.hogar360.catalog_service.domain.model.Category;
import com.hogar360.catalog_service.infraestructure.persistence.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class CategoryServiceTest {

    private CategoryRepository repository;
    private CategoryService service;

    private static final String CATEGORY_NAME_1 = "Category 1";
    private static final String CATEGORY_NAME_2 = "Category 2";

    private static final String CATEGORY_NAME = "Category 1";
    private static final String CATEGORY_ALREADY_EXISTS_MESSAGE = "The category '" + CATEGORY_NAME + "' already exists.";

    @BeforeEach
    void setUp() {
        repository = mock(CategoryRepository.class);
        service = new CategoryService(repository);
    }

    @Test
    void createCategory_successfully() {
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
        Category existingCategory = new Category(1L, CATEGORY_NAME);
        when(repository.findByName(CATEGORY_NAME)).thenReturn(Optional.of(existingCategory));

        CategoryAlreadyExistsException e = assertThrows(
                CategoryAlreadyExistsException.class,
                () -> service.createCategory(CATEGORY_NAME)
        );
        assertThat(e.getMessage()).isEqualTo(CATEGORY_ALREADY_EXISTS_MESSAGE);

        verify(repository, never()).save(any());
    }


    @Test
    void listCategories_successfully() {
        Category category1 = new Category(1L, CATEGORY_NAME_1);
        Category category2 = new Category(2L, CATEGORY_NAME_2);
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Category> categoryPage = new PageImpl<>(Arrays.asList(category1, category2), pageRequest, 2);

        when(repository.findAll(pageRequest)).thenReturn(categoryPage);

        Page<Category> result = service.listCategories(pageRequest, "");

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(CATEGORY_NAME_1, result.getContent().get(0).getName());
        assertEquals(CATEGORY_NAME_2, result.getContent().get(1).getName());
    }

}
