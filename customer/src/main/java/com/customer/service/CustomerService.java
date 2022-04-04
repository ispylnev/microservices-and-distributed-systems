package com.customer.service;

import com.customer.dto.FraudCheckResponse;
import com.customer.entity.Customer;
import com.customer.dto.CustomerRegistrationRequest;
import com.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public record CustomerService(CustomerRepository customerRepository, RestTemplate restTemplate) {
    public void registerCustomer(CustomerRegistrationRequest customerRequest
    ) {
        Customer newCustomer = Customer.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .build();
        customerRepository.saveAndFlush(newCustomer);
        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject("http://localhost:8081/api/v1/api/v1/fraud-ckeck/{customerId}",
                FraudCheckResponse.class,
                newCustomer.getId()
        );
        if (fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("is fraudster");
        }


    }
}
