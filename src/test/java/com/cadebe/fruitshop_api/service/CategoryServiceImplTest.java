package com.cadebe.fruitshop_api.service;

import com.cadebe.fruitshop_api.api.v1.dto.CategoryDTO;
import com.cadebe.fruitshop_api.controller.v1.CategoryController;
import com.cadebe.fruitshop_api.domain.Category;
import com.cadebe.fruitshop_api.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("CategoryServiceImpl")
class CategoryServiceImplTest {

    private final UUID ID = UUID.randomUUID();
    private final String NAME = "cat1";
    private final String URL = CategoryController.BASE_URL + "/" + ID;

    private Category cat1;
    private Category cat2;

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepository);

        cat1 = Category.builder().uuid(ID).name(NAME).build();
        cat2 = Category.builder().uuid(ID).name("cat2").build();
    }

    @Test
    @DisplayName("Test get all categories")
    void getAllCategories() {
        List<Category> categories = Arrays.asList(cat1, cat2);

        when(categoryRepository.findAll()).thenReturn(categories);
        List<CategoryDTO> foundCategories = categoryService.getAllCategories();

        assertThat(foundCategories.size())
                .withFailMessage("Could not find category list with correct size")
                .isEqualTo(2);

        then(categoryRepository).should(times(1)).findAll();

        verify(categoryRepository, times(1)).findAll();
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    @DisplayName("Test get category by id")
    void getCategoryById() {
        Optional<Category> categoryOptional = Optional.of(cat1);

        when(categoryRepository.findById(any(UUID.class))).thenReturn(categoryOptional);

        CategoryDTO customerDTO = categoryService.getCategoryById(ID);

        assertEquals(NAME, customerDTO.getName());

        verify(categoryRepository, times(1)).findById(any(UUID.class));
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    @DisplayName("Test create new category")
    void createNewCategory() {
        CategoryDTO customerDTO = CategoryDTO.builder()
                .uuid(ID)
                .name(NAME)
                .categoryUrl(URL)
                .build();

        Category savedCustomer = Category.builder()
                .uuid(customerDTO.getUuid())
                .name(customerDTO.getName())
                .build();

        when(categoryRepository.save(any(Category.class))).thenReturn(savedCustomer);

        CategoryDTO savedDto = categoryService.createNewCategory(customerDTO);

        assertEquals(customerDTO.getUuid(), savedDto.getUuid());
        assertEquals(customerDTO.getName(), savedDto.getName());

        assertEquals(URL, savedDto.getCategoryUrl());

        verify(categoryRepository, times(1)).save(any(Category.class));
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    @DisplayName("Test update existing category")
    void updateExistingCategory() {
        CategoryDTO customerDTO = CategoryDTO.builder()
                .uuid(ID)
                .name(NAME)
                .categoryUrl(URL)
                .build();

        Category savedCustomer = Category.builder()
                .uuid(customerDTO.getUuid())
                .name(customerDTO.getName())
                .build();

        when(categoryRepository.save(any())).thenReturn(savedCustomer);

        CategoryDTO savedCategory = categoryService.updateExistingCategory(ID, customerDTO);

        assertThat(savedCategory.getUuid())
                .withFailMessage("Could not find category with correct id")
                .isEqualTo(ID);

        assertThat(savedCategory.getName())
                .withFailMessage("Could not find category with correct name")
                .isEqualTo(NAME);

        verify(categoryRepository, times(1)).save(any(Category.class));
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    @DisplayName("Test delete category by id")
    void deleteCategoryById() {
        categoryService.deleteCategoryById(ID);

        then(categoryRepository).should().deleteById(any(UUID.class));

        verify(categoryRepository, times(1)).deleteById(any(UUID.class));
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    @DisplayName("Test delete all categories")
    void deleteAllCategories() {
        categoryService.deleteAllCategories();

        then(categoryRepository).should().deleteAll();

        verify(categoryRepository, times(1)).deleteAll();
        verifyNoMoreInteractions(categoryRepository);
    }
}