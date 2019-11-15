package com.cadebe.fruitshop_api.service;

import com.cadebe.fruitshop_api.api.v1.dto.CategoryDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryById(UUID uuid);

    CategoryDTO createNewCategory(CategoryDTO source);

    CategoryDTO updateExistingCategory(UUID uuid, CategoryDTO source);

    void deleteCategoryById(UUID uuid);

    void deleteAllCategories();
}
