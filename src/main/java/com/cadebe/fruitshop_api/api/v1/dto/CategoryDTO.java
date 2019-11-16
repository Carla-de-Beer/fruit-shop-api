package com.cadebe.fruitshop_api.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@ApiModel(description = "Category")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    @ApiModelProperty(notes = "Category uuid")
    private UUID uuid;

    @ApiModelProperty(notes = "Category name", required = true)
    private String name;

    @ApiModelProperty(notes = "Category category URL")
    @JsonProperty("category_url")
    private String categoryUrl;
}
