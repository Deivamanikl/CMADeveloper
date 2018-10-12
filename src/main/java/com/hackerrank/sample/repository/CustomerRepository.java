package com.hackerrank.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackerrank.sample.model.Customer;

/**
 * .
 */
@Repository("customerRepository")
public interface CustomerRepository extends JpaRepository<Customer,Long>{

}
