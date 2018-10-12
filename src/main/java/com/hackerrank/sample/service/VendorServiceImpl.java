package com.hackerrank.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackerrank.sample.excpetion.BadResourceRequestException;
import com.hackerrank.sample.excpetion.NoSuchResourceFoundException;
import com.hackerrank.sample.model.Vendor;
import com.hackerrank.sample.repository.VendorRepository;

/**
 * .
 */
@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorRepository vendorRepository;



    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    @Override
    public Vendor getVendorById(Long id) {
        Vendor vendor=vendorRepository.findOne(id);
        if(vendor==null)
        {
            throw new NoSuchResourceFoundException("No Vendor with given id found");
        }
        return vendor;
    }

    @Override
    public Vendor createVendor(Vendor vendor) {
        Vendor vendor1=vendorRepository.findOne(vendor.getVendorId());
        if(vendor1!=null)
        {
            throw new BadResourceRequestException("Vendor with same id exists");
        }
        vendorRepository.save(vendor);
        return vendor;
    }

    @Override
    public Vendor updateVendor(Vendor vendor) {
        Vendor vendor1=vendorRepository.findOne(vendor.getVendorId());
        if(vendor1==null)
        {
            throw new NoSuchResourceFoundException("No customer with given id found");
        }
        vendor1.setVendorId(vendor.getVendorId());
        vendor1.setVendorName(vendor.getVendorName());
        vendor1.setVendorContactNo(vendor.getVendorContactNo());
        vendor1.setVendorEmail(vendor.getVendorEmail());
        vendor1.setVendorUsername(vendor.getVendorUsername());
        vendor1.setVendorAddress(vendor.getVendorAddress());
        vendorRepository.save(vendor1);
        return vendor1;
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.delete(id);
    }

    @Override
    public void deleteAllVendors() {
        vendorRepository.deleteAllInBatch();
    }
}
