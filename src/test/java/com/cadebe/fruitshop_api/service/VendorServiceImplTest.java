package com.cadebe.fruitshop_api.service;

import com.cadebe.fruitshop_api.api.v1.dto.VendorDTO;
import com.cadebe.fruitshop_api.api.v1.mapper.VendorMapper;
import com.cadebe.fruitshop_api.controller.v1.VendorController;
import com.cadebe.fruitshop_api.domain.Vendor;
import com.cadebe.fruitshop_api.repository.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test VendorService")
class VendorServiceImplTest {

    private final UUID ID = UUID.randomUUID();
    private final String NAME = "vendor1";
    private final String URL = VendorController.BASE_URL + "/" + ID;

    @Mock
    private VendorRepository vendorRepository;

    private final VendorMapper vendorMapper = new VendorMapper();

    private VendorServiceImpl vendorService;

    private Vendor vendor1;

    @BeforeEach
    void setUp() {
        vendorService = new VendorServiceImpl(vendorRepository, vendorMapper);

        vendor1 = Vendor.builder()
                .name(NAME)
                .build();
    }

    @Test
    @DisplayName("Test get all vendors")
    void getAllVendors() {
        List<Vendor> vendorList = Arrays.asList(Vendor.builder().build(),
                Vendor.builder().build());

        when(vendorRepository.findAll()).thenReturn(vendorList);

        List<VendorDTO> foundVendors = vendorService.getAllVendors();

        assertThat(foundVendors.size())
                .withFailMessage("Could not find vendor list with correct size")
                .isEqualTo(2);

        then(vendorRepository).should(times(1)).findAll();

        verify(vendorRepository, times(1)).findAll();
        verifyNoMoreInteractions(vendorRepository);
    }

    @Test
    @DisplayName("Test get vendor by id")
    void getVendorById() {
        when(vendorRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.ofNullable(vendor1));

        VendorDTO foundVendor = vendorService.getVendorById(ID);

        assertEquals(NAME, foundVendor.getName());

        verify(vendorRepository, times(1)).findById(any(UUID.class));
        verifyNoMoreInteractions(vendorRepository);
    }

    @Test
    @DisplayName("Test create new vendor")
    void createNewVendor() {
        VendorDTO vendorDTO = VendorDTO.builder()
                .uuid(ID)
                .name(NAME)
                .vendorURL(URL)
                .build();

        Vendor savedVendor = Vendor.builder()
                .uuid(vendorDTO.getUuid())
                .name(vendorDTO.getName())
                .build();

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        VendorDTO savedDto = vendorService.createNewVendor(vendorDTO);

        assertEquals(vendorDTO.getName(), savedDto.getName());
        assertEquals(URL, savedDto.getVendorURL());

        verify(vendorRepository, times(1)).save(any(Vendor.class));
        verifyNoMoreInteractions(vendorRepository);
    }

    @Test
    @DisplayName("Test update existing vendor")
    void updateExistingVendor() {
        VendorDTO vendorDTO = VendorDTO.builder()
                .uuid(ID)
                .name(NAME)
                .vendorURL(URL)
                .build();

        Vendor vendor = Vendor.builder()
                .uuid(vendorDTO.getUuid())
                .name(vendorDTO.getName())
                .build();

        when(vendorRepository.save(any())).thenReturn(vendor);

        VendorDTO savedVendor = vendorService.updateExistingVendor(ID, vendorDTO);

        assertThat(savedVendor.getUuid())
                .withFailMessage("Could not find vendor with correct id")
                .isEqualTo(ID);

        assertThat(savedVendor.getName())
                .withFailMessage("Could not find vendor with correct name")
                .isEqualTo(NAME);

        verify(vendorRepository, times(1)).save(any(Vendor.class));
        verifyNoMoreInteractions(vendorRepository);
    }

    @Test
    @DisplayName("Test delete existing vendor by id")
    void deleteVendorById() {
        vendorService.deleteVendorById(ID);

        then(vendorRepository).should().deleteById(ID);

        verify(vendorRepository, times(1)).deleteById(any(UUID.class));
        verifyNoMoreInteractions(vendorRepository);
    }

    @Test
    @DisplayName("Test delete all vendors")
    void deleteAllVendors() {
        vendorService.deleteAllVendors();

        then(vendorRepository).should().deleteAll();

        verify(vendorRepository, times(1)).deleteAll();
        verifyNoMoreInteractions(vendorRepository);
    }
}