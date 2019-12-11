package com.cadebe.fruitshop_api.api.v1.mapper;

import com.cadebe.fruitshop_api.api.v1.dto.CategoryDTO;
import com.cadebe.fruitshop_api.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "categoryUrl",
            expression = "java(com.cadebe.fruitshop_api.controller.v1.CategoryController.BASE_URL + '/' + source.getUuid())")
    CategoryDTO categoryToCategoryDTO(Category source);

    Category categoryDTOToCategory(CategoryDTO source);
}
