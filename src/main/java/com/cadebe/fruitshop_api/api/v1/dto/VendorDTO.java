package com.cadebe.fruitshop_api.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO {

    private UUID uuid;
    private String name;

    @JsonProperty("vendor_url")
    private String vendorURL;
}