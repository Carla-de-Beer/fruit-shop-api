package com.cadebe.fruitshop_api.repository;

import com.cadebe.fruitshop_api.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
