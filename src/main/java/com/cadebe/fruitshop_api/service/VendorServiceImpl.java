package com.cadebe.fruitshop_api.service;

import com.cadebe.fruitshop_api.api.v1.dto.VendorDTO;
import com.cadebe.fruitshop_api.api.v1.mapper.VendorMapper;
import com.cadebe.fruitshop_api.controller.v1.VendorController;
import com.cadebe.fruitshop_api.exception.ResourceNotFoundException;
import com.cadebe.fruitshop_api.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendorMapper::vendorToVendorDTO).filter(Objects::nonNull)
                .peek(categoryDTO -> categoryDTO.setVendorURL(getVendorUrl(categoryDTO.getUuid())))
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(UUID uuid) {
        return vendorRepository.findById(uuid)
                .map(vendorMapper::vendorToVendorDTO)
                .map(vendorDTO -> {
                    vendorDTO.setVendorURL(getVendorUrl(uuid));
                    return vendorDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        return persistAndReturnDTO(vendorDTO);
    }

    @Override
    public VendorDTO updateExistingVendor(UUID uuid, VendorDTO vendorDTO) {
        vendorDTO.setUuid(uuid);
        return persistAndReturnDTO(vendorDTO);
    }

    @Override
    public void deleteVendorById(UUID uuid) {
        vendorRepository.deleteById(uuid);
    }

    @Override
    public void deleteAllVendors() {
        vendorRepository.deleteAll();
    }

    private VendorDTO persistAndReturnDTO(VendorDTO vendorDTO) {
        return vendorMapper.vendorToVendorDTO(vendorRepository.save(vendorMapper.vendorDTOToVendor(vendorDTO)));
    }

    private String getVendorUrl(UUID uuid) {
        return VendorController.BASE_URL + "/" + uuid;
    }
}
