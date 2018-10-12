package com.hackerrank.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackerrank.sample.model.Vendor;

/**
 * .
 */
@Repository("vendorRepository")
public interface VendorRepository extends JpaRepository<Vendor,Long>{


}
