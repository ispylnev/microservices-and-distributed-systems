package com.customer.service;

import com.customer.models.Customer;
import com.customer.dto.CustomerRegistrationRequest;
import com.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository customerRepository) {
    public void registerCustomer(CustomerRegistrationRequest customerRequest
    ) {
        Customer newCustomer = Customer.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .build();
        customerRepository.save(newCustomer);

    }
}
