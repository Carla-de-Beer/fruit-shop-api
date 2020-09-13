package dev.cadebe.fruitshop_api.api.v1.mapper;

import dev.cadebe.fruitshop_api.api.v1.dto.VendorDTO;
import dev.cadebe.fruitshop_api.domain.Vendor;
import dev.cadebe.fruitshop_api.api.v1.dto.VendorDTO;
import dev.cadebe.fruitshop_api.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VendorMapper {

    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    @Mapping(target = "vendorURL",
            expression = "java(dev.cadebe.fruitshop_api.controller.v1.VendorController.BASE_URL + '/' + source.getUuid())")
    VendorDTO vendorToVendorDTO(Vendor source);

    Vendor vendorDTOToVendor(VendorDTO source);
}
