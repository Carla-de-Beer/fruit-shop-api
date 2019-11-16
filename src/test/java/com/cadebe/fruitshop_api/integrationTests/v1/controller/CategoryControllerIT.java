package com.cadebe.fruitshop_api.integrationTests.v1.controller;

import com.cadebe.fruitshop_api.api.v1.dto.CategoryDTO;
import com.cadebe.fruitshop_api.api.v1.mapper.CategoryMapper;
import com.cadebe.fruitshop_api.bootstrap.Bootstrap;
import com.cadebe.fruitshop_api.service.CategoryService;
import com.cadebe.fruitshop_api.service.VendorService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
@DataJpaTest
@Tag("integration")
@DisplayName("Test CategoryController")
class CategoryServiceIT {

    @Autowired
    CategoryService categoryService;

    @Autowired
    VendorService vendorService;

    @BeforeEach
    void setUp() {
        CategoryMapper categoryMapper = new CategoryMapper();

        Bootstrap bootstrap = new Bootstrap(categoryService, vendorService, categoryMapper);
        bootstrap.run();
    }

    @Test
    void getAllCategories() {
    }

    @Test
    void getCategoryById() {
    }

    @Test
    @Transactional
    void createNewCategory() {

    }

    @Test
    @Transactional
    void updateExistingCategory() {
        String updatedName = "UpdatedName";
        UUID id = getCustomerIdValue();

        CategoryDTO originalCategory = categoryService.getCategoryById(id);
        assertNotNull(originalCategory);

        String originalFirstName = originalCategory.getName();

        CategoryDTO categoryDTO = CategoryDTO.builder()
                .name(updatedName)
                .build();

        CategoryDTO updatedCategoryDTO = categoryService.updateExistingCategory(id, categoryDTO);

        CategoryDTO updatedCategory = categoryService.getCategoryById(id);

        assertNotNull(updatedCategory);
        assertEquals(updatedName, updatedCategory.getName());
        assertThat(originalFirstName).isNotEqualTo(Matchers.equalTo(updatedCategory.getName()));
    }

    @Test
    void deleteAllCategories() {
    }

    @Test
    void deleteCategoryById() {
    }

    private UUID getCustomerIdValue() {
        List<CategoryDTO> customers = categoryService.getAllCategories();

        System.out.println("Customers Found: " + customers.size());

        //return first id
        return customers.get(0).getUuid();
    }
}