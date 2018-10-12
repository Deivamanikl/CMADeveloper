package com.hackerrank.sample.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hackerrank.sample.excpetion.BadResourceRequestException;
import com.hackerrank.sample.excpetion.NoSuchResourceFoundException;
import com.hackerrank.sample.model.Vendor;
import com.hackerrank.sample.service.VendorService;

/**
 * .
 */
@RestController
public class VendorController {
    @Autowired
    private VendorService vendorService;

    @RequestMapping(value = "/vendor", method = RequestMethod.GET)
    public ResponseEntity<?> getAllVendors()
    {
        List<Vendor> vendorList=vendorService.getAllVendors();
        if(vendorList.size()==0)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<List>(vendorList,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/vendor/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getVendorById(@PathVariable Long id)
    {
        Vendor vendor=null;
        try
        {
            vendor=vendorService.getVendorById(id);
        }
        catch (NoSuchResourceFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
            return new ResponseEntity<Vendor>(vendor,HttpStatus.OK);
    }

    @RequestMapping(value = "/vendor", method = RequestMethod.POST)
    public ResponseEntity<?> addVendor(@RequestBody Vendor vendor) throws Exception
    {
        Vendor vendor1=null;
        try
        {
            vendor1=vendorService.createVendor(vendor);
        }
        catch (BadResourceRequestException e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Vendor>(vendor,HttpStatus.CREATED);

    }

    @RequestMapping(value = "/vendor/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateVendor(@RequestBody Vendor vendor) throws Exception
    {
        Vendor vendor1=null;
        Vendor vendor2=null;
        try
        {
            vendor1=vendorService.getVendorById(vendor.getVendorId());
            vendor2=vendorService.updateVendor(vendor);
        }
        catch (NoSuchResourceFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Vendor>(vendor2,HttpStatus.OK);
    }

    @RequestMapping(value = "/vendor", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAllVendors()
    {
        vendorService.deleteAllVendors();
        List<Vendor> vendorList=vendorService.getAllVendors();
        if(vendorList.size()==0)
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/vendor/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteVendorById(@PathVariable Long id)
    {
        Vendor vendor=null;
        try
        {
            vendor=vendorService.getVendorById(id);
            vendorService.deleteVendorById(id);
        }
        catch (NoSuchResourceFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
