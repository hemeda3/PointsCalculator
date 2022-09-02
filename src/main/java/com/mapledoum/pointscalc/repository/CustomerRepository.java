package com.mapledoum.pointscalc.repository;

import org.springframework.data.repository.CrudRepository;

import com.mapledoum.pointscalc.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer,Long> {
    Customer findCustomerByCustID(Long customerId);
}
