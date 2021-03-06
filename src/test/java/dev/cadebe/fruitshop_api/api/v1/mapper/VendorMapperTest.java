package dev.cadebe.fruitshop_api.api.v1.mapper;

import dev.cadebe.fruitshop_api.api.v1.dto.VendorDTO;
import dev.cadebe.fruitshop_api.controller.v1.VendorController;
import dev.cadebe.fruitshop_api.domain.Vendor;
import dev.cadebe.fruitshop_api.api.v1.dto.VendorDTO;
import dev.cadebe.fruitshop_api.controller.v1.VendorController;
import dev.cadebe.fruitshop_api.domain.Vendor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("mapper")
@DisplayName("VendorMapper")
class VendorMapperTest {

    private final UUID ID = UUID.randomUUID();
    private final String NAME = "vendor1";
    private final String URL = VendorController.BASE_URL + "/" + ID;

    @Test
    @DisplayName("Test vendor to vendorDTO id")
    void vendorToVendorDTO() {
        Vendor vendor = Vendor.builder()
                .uuid(ID)
                .name(NAME)
                .build();

        VendorDTO vendorDTO = VendorMapper.INSTANCE.vendorToVendorDTO(vendor);

        assert vendorDTO != null;
        assertThat(vendorDTO.getUuid()).isEqualTo(vendor.getUuid());
        assertThat(vendorDTO.getName()).isEqualTo(vendor.getName());
    }

    @Test
    @DisplayName("Test vendor to vendorDTO id (null input)")
    void vendorToVendorDTOWithNullInput() {
        VendorDTO mappedVendorDTO = VendorMapper.INSTANCE.vendorToVendorDTO(null);

        assertThat(mappedVendorDTO).isNull();
    }

    @Test
    @DisplayName("Test vendorDTO to vendor id")
    void vendorDTOToVendor() {
        VendorDTO vendorDTO = VendorDTO.builder()
                .uuid(ID)
                .name(NAME)
                .vendorURL(URL)
                .build();

        Vendor vendor = VendorMapper.INSTANCE.vendorDTOToVendor(vendorDTO);

        assertThat(vendor).isEqualToComparingFieldByField(vendorDTO);
    }

    @Test
    @DisplayName("Test vendorDTO to vendor id (null input)")
    void vendorDTOToVendorWithNullInput() {
        Vendor mappedVendor = VendorMapper.INSTANCE.vendorDTOToVendor(null);

        assertThat(mappedVendor).isNull();
    }
}