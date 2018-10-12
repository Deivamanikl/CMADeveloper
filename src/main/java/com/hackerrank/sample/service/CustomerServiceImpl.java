package com.hackerrank.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hackerrank.sample.excpetion.BadResourceRequestException;
import com.hackerrank.sample.excpetion.NoSuchResourceFoundException;
import com.hackerrank.sample.model.Customer;
import com.hackerrank.sample.repository.CustomerRepository;

/**
 * .
 */
@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    @Qualifier("customerRepository")
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        Customer customer=customerRepository.findOne(id);
        if(customer==null)
        {
            throw new NoSuchResourceFoundException("No customer with given id found.");
        }
        return customer;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer customer1=null;
        customer1=customerRepository.findOne(customer.getCustomerId());
        if(customer1!=null)
        {
            throw new BadResourceRequestException("Customer with same id exists.");
        }
        customerRepository.save(customer);
        return customer;
    }

    public Customer updateCustomer(Customer customer)
    {
        Customer customer1=customerRepository.findOne(customer.getCustomerId());
        if(customer1==null)
        {
            throw new NoSuchResourceFoundException("No customer with given id found");
        }
        customer1.setCustomerId(customer.getCustomerId());
        customer1.setCustomerName(customer.getCustomerName());
        customer1.setContactNumber(customer.getContactNumber());
        customer1.setAddress(customer.getAddress());
        customer1.setGender(customer.getGender());
        customerRepository.save(customer1);
        return customer1;
    }
    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.delete(id);
    }
    @Override
    public void deleteAllCustomers() {
        customerRepository.deleteAllInBatch();
    }








}
