package com.cadebe.fruitshop_api.service;

import com.cadebe.fruitshop_api.api.v1.dto.CategoryDTO;
import com.cadebe.fruitshop_api.api.v1.mapper.CategoryMapper;
import com.cadebe.fruitshop_api.bootstrap.Bootstrap;
import com.cadebe.fruitshop_api.exception.ResourceNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("integration")
@DisplayName("CategoryService (IT)")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class CategoryServiceIT {

    @Autowired
    CategoryService categoryService;

    @Autowired
    VendorService vendorService;

    @BeforeEach
    void setUp() {
        Bootstrap bootstrap = new Bootstrap(categoryService, vendorService);
        bootstrap.run();
    }

    @Test
    @DisplayName("Test get all categories")
    void getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();

        assertThat(categories.size())
                .withFailMessage("All of the Bootstrap categories could not be found")
                .isEqualTo(5);
    }

    @Test
    @DisplayName("Test get category by id")
    void getCategoryById() {
        UUID id = getFirstCategoryIdValue();
        CategoryDTO foundCategory = categoryService.getCategoryById(id);

        assertNotNull(foundCategory);
    }

    @Test
    @DisplayName("Test get category by id (not found)")
    void getCategoryByIdNotFound() {
        UUID id = UUID.fromString("a1a1a1a1-a1a1-a1a1-a1a1-a1a1a1a1a1a");

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.getCategoryById(id);
        });
    }

    @Test
    @Transactional
    @DisplayName("Test create new category")
    void createNewCategory() {
        String name = "new1";
        List<CategoryDTO> categoriesList1 = categoryService.getAllCategories();

        CategoryDTO categoryDTO = CategoryDTO.builder()
                .uuid(UUID.randomUUID())
                .name(name)
                .build();

        CategoryDTO savedCategoryDTO = categoryService.createNewCategory(categoryDTO);

        List<CategoryDTO> categoriesList2 = categoryService.getAllCategories();

        assertThat(savedCategoryDTO.getName())
                .withFailMessage("New category's name has does not match")
                .isEqualTo(name);

        assertThat(categoriesList1.size())
                .withFailMessage("New category has not been added")
                .isEqualTo(categoriesList2.size() - 1);
    }

    @Test
    @Transactional
    @DisplayName("Test update existing category by id")
    void updateExistingCategory() {
        String updatedName = "sweets";
        UUID id = getFirstCategoryIdValue();

        // Get a category
        CategoryDTO originalCategory = categoryService.getCategoryById(id);
        assertNotNull(originalCategory);

        String originalName = originalCategory.getName();

        CategoryDTO categoryDTO = CategoryDTO.builder()
                .uuid(originalCategory.getUuid())
                .name(updatedName)
                .build();

        // update it
        categoryService.updateExistingCategory(id, categoryDTO);

        // check the update
        CategoryDTO updatedCategory = categoryService.getCategoryById(id);

        assertNotNull(updatedCategory);

        assertEquals(updatedName, updatedCategory.getName(), () -> "Updated category's new name does not match");

        assertThat(originalName)
                .withFailMessage("Updated category's new name has not been updated")
                .isNotEqualTo(updatedCategory.getName());
    }

    @Test
    @Transactional
    @DisplayName("Test delete all categories")
    void deleteAllCategories() {
        List<CategoryDTO> categoryList = categoryService.getAllCategories();

        categoryService.deleteAllCategories();

        List<CategoryDTO> emptyCategoryList = categoryService.getAllCategories();

        assertThat(emptyCategoryList.size())
                .withFailMessage("Size of deleted list is not zero")
                .isZero();

        assertThat(categoryList.size())
                .withFailMessage("Size of the two lists is not equal")
                .isNotEqualTo(emptyCategoryList.size());
    }

    @Test
    @Transactional
    @DisplayName("Test delete category by id")
    void deleteCategoryById() {
        List<CategoryDTO> categoryList1 = categoryService.getAllCategories();

        UUID id = getFirstCategoryIdValue();
        categoryService.getCategoryById(id);

        categoryService.deleteCategoryById(id);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.getCategoryById(id);
        });

        List<CategoryDTO> categoryList2 = categoryService.getAllCategories();

        assertThat(categoryList1.size() - 1)
                .withFailMessage("Category has not been deleted")
                .isEqualTo(categoryList2.size());
    }

    private UUID getFirstCategoryIdValue() {
        return categoryService.getAllCategories().get(0).getUuid();
    }
}