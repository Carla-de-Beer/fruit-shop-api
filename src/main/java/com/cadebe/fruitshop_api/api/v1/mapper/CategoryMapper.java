package com.cadebe.fruitshop_api.api.v1.mapper;

import com.cadebe.fruitshop_api.api.v1.dto.CategoryDTO;
import com.cadebe.fruitshop_api.domain.Category;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    @Synchronized
    @Nullable
    public CategoryDTO categoryToCategoryDTO(Category source) {
        if (source == null) {
            return null;
        }

        return CategoryDTO.builder()
                .uuid(source.getUuid())
                .name(source.getName())
                .categoryUrl(source.getCategoryUrl())
                .build();
    }

    @Synchronized
    @Nullable
    public Category categoryDTOToCategory(CategoryDTO source) {
        if (source == null) {
            return null;
        }

        return Category.builder()
                .uuid(source.getUuid())
                .name(source.getName())
                .categoryUrl(source.getCategoryUrl())
                .build();
    }
}