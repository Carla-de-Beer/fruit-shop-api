package dev.cadebe.fruitshop_api.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@ApiModel(description = "Vendor")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO {

    @ApiModelProperty(notes = "Vendor uuid")
    private UUID uuid;

    @ApiModelProperty(notes = "Vendor name", required = true)
    private String name;

    @ApiModelProperty(notes = "Vendor category URL")
    @JsonProperty("vendor_url")
    private String vendorURL;
}