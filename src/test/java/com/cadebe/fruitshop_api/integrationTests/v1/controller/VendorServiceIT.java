package com.cadebe.fruitshop_api.integrationTests.v1.controller;

import com.cadebe.fruitshop_api.api.v1.dto.VendorDTO;
import com.cadebe.fruitshop_api.api.v1.mapper.CategoryMapper;
import com.cadebe.fruitshop_api.bootstrap.Bootstrap;
import com.cadebe.fruitshop_api.exception.ResourceNotFoundException;
import com.cadebe.fruitshop_api.service.CategoryService;
import com.cadebe.fruitshop_api.service.VendorService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("integration")
@DisplayName("Test VendorService (IT)")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class VendorServiceIT {

    @Autowired
    CategoryService categoryService;

    @Autowired
    VendorService vendorService;

    @BeforeEach
    void setUp() {
        CategoryMapper categoryMapper = new CategoryMapper();

        Bootstrap bootstrap = new Bootstrap(categoryService, vendorService, categoryMapper);
        bootstrap.run();
    }

    @Test
    @Transactional
    @DisplayName("Test get all vendors")
    void getAllVendors() {
        List<VendorDTO> vendors = vendorService.getAllVendors();

        assertThat(vendors.size())
                .withFailMessage("All of the Bootstrap vendors could not be found")
                .isEqualTo(2);
    }

    @Test
    @Transactional
    @DisplayName("Test get vendor by id")
    void getVendorById() {
        UUID id = getFirstVendorIdValue();
        VendorDTO foundVendor = vendorService.getVendorById(id);

        assertNotNull(foundVendor);
    }

    @Test
    @Transactional
    @DisplayName("Test create new vendor")
    void createNewVendor() {
        String name = "new1";
        List<VendorDTO> vendorsList1 = vendorService.getAllVendors();

        VendorDTO vendorDTO = VendorDTO.builder()
                .uuid(UUID.randomUUID())
                .name(name)
                .build();

        VendorDTO savedVendorDTO = vendorService.createNewVendor(vendorDTO);

        List<VendorDTO> vendorsList2 = vendorService.getAllVendors();

        assertThat(savedVendorDTO.getName())
                .withFailMessage("New vendor's name has does not match")
                .isEqualTo(name);

        assertThat(vendorsList1.size())
                .withFailMessage("New vendor has not been added")
                .isEqualTo(vendorsList2.size() - 1);
    }

    @Test
    @Transactional
    @DisplayName("Test update existing vendor by id")
    void updateExistingVendor() {
        String updatedName = "Mario";
        UUID id = getFirstVendorIdValue();

        // Get a vendor
        VendorDTO originalVendor = vendorService.getVendorById(id);
        assertNotNull(originalVendor);

        String originalName = originalVendor.getName();

        VendorDTO vendorDTO = VendorDTO.builder()
                .uuid(originalVendor.getUuid())
                .name(updatedName)
                .build();

        // update it
        vendorService.updateExistingVendor(id, vendorDTO);

        // check the update
        VendorDTO updatedVendor = vendorService.getVendorById(id);

        assertNotNull(updatedVendor);

        assertEquals(updatedName, updatedVendor.getName(), () -> "Updated vendor's new name does not match");

        assertThat(originalName)
                .withFailMessage("Updated vendor's new name has not been updated")
                .isNotEqualTo(updatedVendor.getName());
    }

    @Test
    @Transactional
    @DisplayName("Test delete all vendors")
    void deleteAllVendors() {
        List<VendorDTO> vendorList = vendorService.getAllVendors();

        vendorService.deleteAllVendors();

        List<VendorDTO> emptyVendorList = vendorService.getAllVendors();

        assertThat(emptyVendorList.size())
                .withFailMessage("Size of deleted list is not zero")
                .isZero();

        assertThat(vendorList.size())
                .withFailMessage("Size of the two lists is not equal")
                .isNotEqualTo(emptyVendorList.size());
    }

    @Test
    @Transactional
    @DisplayName("Test delete vendor by id")
    void deleteVendorById() {
        List<VendorDTO> vendorList1 = vendorService.getAllVendors();

        UUID id = getFirstVendorIdValue();
        vendorService.getVendorById(id);

        vendorService.deleteVendorById(id);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            vendorService.getVendorById(id);
        });

        List<VendorDTO> vendorList2 = vendorService.getAllVendors();

        assertThat(vendorList1.size() - 1)
                .withFailMessage("Vendor has not been deleted")
                .isEqualTo(vendorList2.size());
    }

    private UUID getFirstVendorIdValue() {
        return vendorService.getAllVendors().get(0).getUuid();
    }
}
