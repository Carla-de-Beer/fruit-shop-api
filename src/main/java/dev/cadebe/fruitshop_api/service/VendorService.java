package dev.cadebe.fruitshop_api.service;

import dev.cadebe.fruitshop_api.api.v1.dto.VendorDTO;

import java.util.List;
import java.util.UUID;

public interface VendorService {

    List<VendorDTO> getAllVendors();

    VendorDTO getVendorById(UUID uuid);

    VendorDTO createNewVendor(VendorDTO source);

    VendorDTO updateExistingVendor(UUID uuid, VendorDTO source);

    void deleteVendorById(UUID uuid);

    void deleteAllVendors();
}
