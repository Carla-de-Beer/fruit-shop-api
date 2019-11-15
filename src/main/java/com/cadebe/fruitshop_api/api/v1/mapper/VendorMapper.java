package com.cadebe.fruitshop_api.api.v1.mapper;

import com.cadebe.fruitshop_api.api.v1.dto.VendorDTO;
import com.cadebe.fruitshop_api.domain.Vendor;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class VendorMapper {

    @Synchronized
    @Nullable
    public VendorDTO vendorToVendorDTO(Vendor source) {
        if (source == null) {
            return null;
        }

        return VendorDTO.builder()
                .uuid(source.getUuid())
                .name(source.getName())
                .vendorURL(source.getVendorURL())
                .build();
    }

    @Synchronized
    @Nullable
    public Vendor vendorDTOToVendor(VendorDTO source) {
        if (source == null) {
            return null;
        }

        return Vendor.builder()
                .uuid(source.getUuid())
                .name(source.getName())
                .vendorURL(source.getVendorURL())
                .build();
    }
}
