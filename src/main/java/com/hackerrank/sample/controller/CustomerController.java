package com.hackerrank.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hackerrank.sample.excpetion.BadResourceRequestException;
import com.hackerrank.sample.excpetion.NoSuchResourceFoundException;
import com.hackerrank.sample.model.Customer;
import com.hackerrank.sample.service.CustomerService;

/**
 * .
 */
@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getCustomers()
    {
        List<Customer> customerList=customerService.getAllCustomers();
        if(customerList.size()==0)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<List>(customerList,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/customer/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getCustomerById(@PathVariable Long id)
    {
        Customer customer=null;
        try
        {
            customer=customerService.getCustomerById(id);
        }
        catch (NoSuchResourceFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
            return new ResponseEntity<Customer>(customer,HttpStatus.OK);

    }

    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> addCustomer(@RequestBody Customer customer) throws Exception
    {
        Customer customer1=null;
        try
        {
            customer1=customerService.createCustomer(customer);
        }
        catch(BadResourceRequestException e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
         return new ResponseEntity<Customer>(customer1,HttpStatus.CREATED);

    }

    @RequestMapping(value = "/customer/{id}", method = RequestMethod.PUT)
    public @ResponseBody ResponseEntity<?> updateCustomer(@RequestBody Customer customer)
    {
        Customer customer1=null;
        Customer customer2=null;
        try
        {
            customer1=customerService.getCustomerById(customer.getCustomerId());
            customer2=customerService.updateCustomer(customer);
        }
        catch(NoSuchResourceFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Customer>(customer2,HttpStatus.OK);

    }

    @RequestMapping(value = "/customer", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<?> deleteAllCustomers()
    {
        customerService.deleteAllCustomers();
        List<Customer> customerList=customerService.getAllCustomers();
        if(customerList.size()==0)
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<?> deleteCustomerById(@PathVariable Long id)
    {
        Customer customer=null;
        try
        {
            customer=customerService.getCustomerById(id);
            customerService.deleteCustomerById(id);
        }
        catch (NoSuchResourceFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
