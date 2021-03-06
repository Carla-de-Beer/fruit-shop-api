package dev.cadebe.fruitshop_api.api.v1.mapper;

import dev.cadebe.fruitshop_api.api.v1.dto.CategoryDTO;
import dev.cadebe.fruitshop_api.controller.v1.CategoryController;
import dev.cadebe.fruitshop_api.domain.Category;
import dev.cadebe.fruitshop_api.api.v1.dto.CategoryDTO;
import dev.cadebe.fruitshop_api.controller.v1.CategoryController;
import dev.cadebe.fruitshop_api.domain.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("mapper")
@DisplayName("CategoryMapper")
class CategoryMapperTest {

    private final UUID ID = UUID.randomUUID();
    private final String NAME = "category1";
    private final String URL = CategoryController.BASE_URL + "/" + ID;

    @Test
    @DisplayName("Test Category to CategoryDTO")
    void categoryToCategoryDTO() {
        Category category = Category.builder()
                .uuid(ID)
                .name(NAME)
                .build();

        CategoryDTO categoryDTO = CategoryMapper.INSTANCE.categoryToCategoryDTO(category);

        assert categoryDTO != null;
        assertThat(categoryDTO.getName())
                .withFailMessage("Could not map from Category name to CategoryDTO name")
                .isEqualTo(category.getName());

        assertThat(categoryDTO.getUuid())
                .withFailMessage("Could not map from Category uuid to CategoryDTO uuid")
                .isEqualTo(category.getUuid());
    }

    @Test
    @DisplayName("Test Category to CategoryDTO (null input)")
    void categoryToCategoryDTOWithNullInput() {
        CategoryDTO mappedCategoryDTO = CategoryMapper.INSTANCE.categoryToCategoryDTO(null);

        assertThat(mappedCategoryDTO)
                .withFailMessage("Could not map from Category to CategoryDTO")
                .isNull();
    }

    @Test
    @DisplayName("Test CategoryDTO to Category")
    void categoryDTOToCategory() {
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .uuid(ID)
                .name(NAME)
                .categoryUrl(URL)
                .build();

        Category category = CategoryMapper.INSTANCE.categoryDTOToCategory(categoryDTO);

        assertThat(category)
                .withFailMessage("Could not map from CategoryDTO to Category")
                .isEqualToComparingFieldByField(categoryDTO);
    }

    @Test
    @DisplayName("Test CategoryDTO to Category (null input)")
    void categoryDTOToCategoryWithNullInput() {
        Category mappedCategory = CategoryMapper.INSTANCE.categoryDTOToCategory(null);

        assertThat(mappedCategory)
                .withFailMessage("Could not map from CategoryDTO to Category")
                .isNull();
    }
}