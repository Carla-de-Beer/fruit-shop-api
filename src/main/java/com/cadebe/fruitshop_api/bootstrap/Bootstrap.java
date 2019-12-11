package com.cadebe.fruitshop_api.bootstrap;

import com.cadebe.fruitshop_api.api.v1.dto.CategoryDTO;
import com.cadebe.fruitshop_api.api.v1.dto.VendorDTO;
import com.cadebe.fruitshop_api.service.CategoryService;
import com.cadebe.fruitshop_api.service.VendorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {

    private final CategoryService categoryService;
    private final VendorService vendorService;

    public Bootstrap(CategoryService categoryService, VendorService vendorService) {
        this.categoryService = categoryService;
        this.vendorService = vendorService;
    }

    @Override
    public void run(String... args) {
        loadCategories();
        loadVendors();
    }

    private void loadCategories() {
        if (categoryService.getAllCategories().size() == 0) {

            CategoryDTO fruits = CategoryDTO.builder()
                    .uuid(UUID.randomUUID())
                    .name("Fruits")
                    .build();

            categoryService.createNewCategory(fruits);

            CategoryDTO fresh = CategoryDTO.builder()
                    .uuid(UUID.randomUUID())
                    .name("Fresh")
                    .build();

            categoryService.createNewCategory(fresh);

            CategoryDTO dried = CategoryDTO.builder()
                    .uuid(UUID.randomUUID())
                    .name("Dried")
                    .build();

            categoryService.createNewCategory(dried);

            CategoryDTO nuts = CategoryDTO.builder()
                    .uuid(UUID.randomUUID())
                    .name("Nuts")
                    .build();

            categoryService.createNewCategory(nuts);

            CategoryDTO exotic = CategoryDTO.builder()
                    .uuid(UUID.randomUUID())
                    .name("Exotic")
                    .build();

            categoryService.createNewCategory(exotic);

            //log.info("Categories load: " + categoryService.getAllCategories().size());
        }
    }

    private void loadVendors() {
        if (vendorService.getAllVendors().size() == 0) {
            VendorDTO alfred = VendorDTO.builder()
                    .uuid(UUID.randomUUID())
                    .name("Alfreds Futterkiste")
                    .build();

            vendorService.createNewVendor(alfred);

            VendorDTO gino = VendorDTO.builder()
                    .uuid(UUID.randomUUID())
                    .name("Ginos Gelato")
                    .build();

            vendorService.createNewVendor(gino);
        }
    }
}
