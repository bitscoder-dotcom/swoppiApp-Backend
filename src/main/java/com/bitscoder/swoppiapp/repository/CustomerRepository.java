package com.bitscoder.swoppiapp.repository;

import com.bitscoder.swoppiapp.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findCustomerByEmail(String email);
}
