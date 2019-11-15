package com.cadebe.fruitshop_api.api.v1.mapper;

import com.cadebe.fruitshop_api.api.v1.dto.CategoryDTO;
import com.cadebe.fruitshop_api.controller.v1.CategoryController;
import com.cadebe.fruitshop_api.domain.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("mapper")
@DisplayName("Test CategoryMapper")
class CategoryMapperTest {

    private final UUID ID = UUID.randomUUID();
    private final String NAME = "category1";
    private final String URL = CategoryController.BASE_URL + "/" + ID;

    private CategoryMapper categoryMapper = new CategoryMapper();

    @Test
    @DisplayName("Test category to categoryDTO id")
    void categoryToCategoryDTO() {
        Category category = Category.builder()
                .uuid(ID)
                .name(NAME)
                .categoryUrl(URL)
                .build();

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        assertThat(categoryDTO).isEqualToComparingFieldByField(category);
    }

    @Test
    @DisplayName("Test category to categoryDTO id (null input)")
    void categoryToCategoryDTOWithNullInput() {
        CategoryDTO mappedCategoryDTO = categoryMapper.categoryToCategoryDTO(null);

        assertThat(mappedCategoryDTO).isNull();
    }

    @Test
    @DisplayName("Test categoryDTO to category id")
    void categoryDTOToCategory() {
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .uuid(ID)
                .name(NAME)
                .categoryUrl(URL)
                .build();

        Category category = categoryMapper.categoryDTOToCategory(categoryDTO);

        assertThat(category).isEqualToComparingFieldByField(categoryDTO);
    }

    @Test
    @DisplayName("Test categoryDTO to category id (null input)")
    void categoryDTOToCategoryWithNullInput() {
        Category mappedCategory = categoryMapper.categoryDTOToCategory(null);

        assertThat(mappedCategory).isNull();
    }
}