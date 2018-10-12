package com.hackerrank.sample.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hackerrank.sample.model.Customer;

/**
 * .
 */
@Service
public interface CustomerService {

    void deleteAllCustomers();
    void deleteCustomerById(Long id);

    Customer createCustomer(Customer customer);

    Customer getCustomerById(Long id);

    List<Customer> getAllCustomers();
    Customer updateCustomer(Customer customer);
}
