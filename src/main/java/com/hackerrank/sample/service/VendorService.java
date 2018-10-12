package com.hackerrank.sample.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hackerrank.sample.model.Vendor;

/**
 * .
 */
@Service
public interface VendorService {

    List<Vendor> getAllVendors();
    Vendor getVendorById(Long id);
    Vendor createVendor(Vendor vendor);
    Vendor updateVendor(Vendor vendor);
    void deleteVendorById(Long id);
    void deleteAllVendors();

}
