package com.cadebe.fruitshop_api.controller.v1;

import com.cadebe.fruitshop_api.api.v1.dto.CategoryDTO;
import com.cadebe.fruitshop_api.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping({CategoryController.BASE_URL, CategoryController.BASE_URL + "/"})
public class CategoryController {

    public static final String BASE_URL = "/api/v1/categories";
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getCategoryById(@PathVariable String id) {
        return categoryService.getCategoryById(UUID.fromString(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDTO createNewCategory(@RequestBody CategoryDTO category) {
        return categoryService.createNewCategory(category);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO updateExistingCategory(@PathVariable String id, @RequestBody CategoryDTO category) {
        return categoryService.updateExistingCategory(UUID.fromString(id), category);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllCategories() {
        categoryService.deleteAllCategories();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable String id) {
        categoryService.deleteCategoryById(UUID.fromString(id));
    }
}
