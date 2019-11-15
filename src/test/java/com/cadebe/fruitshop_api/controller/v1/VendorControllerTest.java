package com.cadebe.fruitshop_api.controller.v1;

import com.cadebe.fruitshop_api.api.v1.dto.VendorDTO;
import com.cadebe.fruitshop_api.exception.ResourceNotFoundException;
import com.cadebe.fruitshop_api.exception.RestResponseEntityExceptionHandler;
import com.cadebe.fruitshop_api.service.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.cadebe.fruitshop_api.controller.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("controller")
@DisplayName("Test VendorController")
@ExtendWith(MockitoExtension.class)
class VendorControllerTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NAME = "vendor1";

    @Mock
    private VendorService vendorService;

    @InjectMocks
    private VendorController vendorController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Add exception handler here
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Test get all vendors")
    void getAllVendors() throws Exception {
        List<VendorDTO> vendors = Arrays.asList(VendorDTO.builder().build(), VendorDTO.builder().build());

        when(vendorService.getAllVendors()).thenReturn(vendors);

        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test get vendor by id")
    void getVendorById() throws Exception {
        when(vendorService.getVendorById(any(UUID.class))).thenReturn(VendorDTO.builder()
                .uuid(ID)
                .name(NAME)
                .build());

        mockMvc.perform(get(VendorController.BASE_URL + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid", equalTo(ID.toString())));
    }

    @Test
    @DisplayName("Test create new vendor")
    void createNewVendor() throws Exception {
        VendorDTO vendor = VendorDTO.builder()
                .uuid(ID)
                .name(NAME)
                .vendorURL(VendorController.BASE_URL + "/" + ID)
                .build();

        VendorDTO returnDTO =  VendorDTO.builder()
                .uuid(ID)
                .name(NAME)
                .vendorURL(vendor.getVendorURL())
                .build();

        when(vendorService.createNewVendor(vendor)).thenReturn(returnDTO);

        mockMvc.perform(post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/" + ID)));
    }

    @Test
    @DisplayName("Test update existing vendor")
    void updateExistingVendor() throws Exception {
        VendorDTO vendor = VendorDTO.builder()
                .uuid(ID)
                .name(NAME)
                .build();

        VendorDTO returnDTO = VendorDTO.builder()
                .uuid(vendor.getUuid())
                .name(vendor.getName())
                .build();

        when(vendorService.updateExistingVendor(any(UUID.class), any(VendorDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(put(VendorController.BASE_URL + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    @DisplayName("Test delete vendor by id")
    void deleteAllVendors() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_URL + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(vendorService).deleteVendorById(any(UUID.class));
    }

    @Test
    @DisplayName("Test delete all vendors")
    void deleteVendorById() throws Exception {
        when(vendorService.getVendorById(any(UUID.class))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(VendorController.BASE_URL + "/a1a1a1a1-a1a1-a1a1-a1a1-a1a1a1a1a1a")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}