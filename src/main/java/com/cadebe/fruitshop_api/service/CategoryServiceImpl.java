package com.cadebe.fruitshop_api.service;

import com.cadebe.fruitshop_api.api.v1.dto.CategoryDTO;
import com.cadebe.fruitshop_api.api.v1.mapper.CategoryMapper;
import com.cadebe.fruitshop_api.controller.v1.CategoryController;
import com.cadebe.fruitshop_api.exception.ResourceNotFoundException;
import com.cadebe.fruitshop_api.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository ) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper.INSTANCE::categoryToCategoryDTO).filter(Objects::nonNull)
                .peek(categoryDTO -> categoryDTO.setCategoryUrl(getCategoryUrl(categoryDTO.getUuid())))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(UUID uuid) {
        return categoryRepository.findById(uuid)
                .map(CategoryMapper.INSTANCE::categoryToCategoryDTO)
                .map(categoryDTO -> {
                    categoryDTO.setCategoryUrl(getCategoryUrl(uuid));
                    return categoryDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CategoryDTO createNewCategory(CategoryDTO categoryDTO) {
        return persistAndReturnDTO(categoryDTO);
    }

    @Override
    public CategoryDTO updateExistingCategory(UUID uuid, CategoryDTO categoryDTO) {
        return persistAndReturnDTO(categoryDTO);
    }

    @Override
    public void deleteCategoryById(UUID uuid) {
        categoryRepository.deleteById(uuid);
    }

    @Override
    public void deleteAllCategories() {
        categoryRepository.deleteAll();
    }

    private CategoryDTO persistAndReturnDTO(CategoryDTO categoryDTO) {
        return CategoryMapper.INSTANCE.categoryToCategoryDTO(categoryRepository.save(CategoryMapper.INSTANCE.categoryDTOToCategory(categoryDTO)));
    }

    private String getCategoryUrl(UUID uuid) {
        return CategoryController.BASE_URL + "/" + uuid;
    }
}
