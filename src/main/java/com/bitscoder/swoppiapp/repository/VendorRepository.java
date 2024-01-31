package com.bitscoder.swoppiapp.repository;

import com.bitscoder.swoppiapp.entities.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, String> {
    Optional<Vendor> findVendorByEmail(String email);
}
