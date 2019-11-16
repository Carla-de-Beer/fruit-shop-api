package com.cadebe.fruitshop_api.controller.v1;

import com.cadebe.fruitshop_api.api.v1.dto.CategoryDTO;
import com.cadebe.fruitshop_api.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(tags = {"Category Controller"})
@SwaggerDefinition(tags = {
        @Tag(name = "Category Controller", description = "Category Controller for the Fruit Shop API")
})
@RestController
@RequestMapping({CategoryController.BASE_URL, CategoryController.BASE_URL + "/"})
public class CategoryController {

    public static final String BASE_URL = "/api/v1/categories";
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ApiOperation(value = "View List of Categories")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @ApiOperation(value = "View Category Specified by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getCategoryById(@PathVariable String id) {
        return categoryService.getCategoryById(UUID.fromString(id));
    }

    @ApiOperation(value = "Create New Category")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDTO createNewCategory(@RequestBody CategoryDTO category) {
        return categoryService.createNewCategory(category);
    }

    @ApiOperation(value = "Update Existing Category")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO updateExistingCategory(@PathVariable String id, @RequestBody CategoryDTO category) {
        return categoryService.updateExistingCategory(UUID.fromString(id), category);
    }

    @ApiOperation(value = "Delete All Categories")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllCategories() {
        categoryService.deleteAllCategories();
    }

    @ApiOperation(value = "Delete Category Specified by ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable String id) {
        categoryService.deleteCategoryById(UUID.fromString(id));
    }
}
