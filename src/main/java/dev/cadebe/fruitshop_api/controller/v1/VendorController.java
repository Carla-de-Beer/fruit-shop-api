package dev.cadebe.fruitshop_api.controller.v1;

import dev.cadebe.fruitshop_api.api.v1.dto.VendorDTO;
import dev.cadebe.fruitshop_api.service.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(tags = {"Vendor Controller"})
@SwaggerDefinition(tags = {
        @Tag(name = "Vendor Controller", description = "Vendor Controller for the Fruit Shop API")
})
@RestController
@RequestMapping({VendorController.BASE_URL, VendorController.BASE_URL + "/"})
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @ApiOperation(value = "View List of Vendors")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VendorDTO> getAllVendors() {
        return vendorService.getAllVendors();
    }

    @ApiOperation(value = "View Vendor Specified by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable String id) {
        return vendorService.getVendorById(UUID.fromString(id));
    }

    @ApiOperation(value = "Create New Vendor")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {
        return vendorService.createNewVendor(vendorDTO);
    }

    @ApiOperation(value = "Update Existing Vendor")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateExistingVendor(@PathVariable String id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.updateExistingVendor(UUID.fromString(id), vendorDTO);
    }

    @ApiOperation(value = "Delete All Vendors")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllVendors() {
        vendorService.deleteAllVendors();
    }

    @ApiOperation(value = "Delete Vendor Specified by ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVendorById(@PathVariable String id) {
        vendorService.deleteVendorById(UUID.fromString(id));
    }
}
