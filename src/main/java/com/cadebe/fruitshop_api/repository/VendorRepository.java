package com.cadebe.fruitshop_api.repository;

import com.cadebe.fruitshop_api.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VendorRepository extends JpaRepository<Vendor, UUID> {
}
