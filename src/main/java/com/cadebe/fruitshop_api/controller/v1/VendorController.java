package com.cadebe.fruitshop_api.controller.v1;

import com.cadebe.fruitshop_api.api.v1.dto.VendorDTO;
import com.cadebe.fruitshop_api.service.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping({VendorController.BASE_URL, VendorController.BASE_URL + "/"})
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VendorDTO> getAllVendors() {
        return vendorService.getAllVendors();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable String id) {
        return vendorService.getVendorById(UUID.fromString(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {
        return vendorService.createNewVendor(vendorDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateExistingVendor(@PathVariable String id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.updateExistingVendor(UUID.fromString(id), vendorDTO);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllVendors() {
        vendorService.deleteAllVendors();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVendorById(@PathVariable String id) {
        vendorService.deleteVendorById(UUID.fromString(id));
    }
}
