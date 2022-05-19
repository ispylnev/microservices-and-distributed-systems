package com.customer.service;

import com.amqp.service.RabbitMqMessageProducer;
import com.clients.fraud.FraudCheckResponse;
import com.clients.fraud.FraudClient;
import com.clients.notification.NotificationClient;
import com.clients.notification.NotificationRequest;
import com.customer.entity.Customer;
import com.customer.dto.CustomerRegistrationRequest;
import com.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository customerRepository,
                              NotificationClient notificationClient,
                              FraudClient fraudClient,
                              RabbitMqMessageProducer rabbitMqMessageProducer
) {


    public void registerCustomer(CustomerRegistrationRequest customerRequest
    ) {
        Customer newCustomer = Customer.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .build();
        customerRepository.saveAndFlush(newCustomer);
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(newCustomer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("is fraudster");
        }

        NotificationRequest notificationRequest = new NotificationRequest(
                newCustomer.getId(),
                newCustomer.getEmail(),
                String.format("Hi %s, welcome to system...",
                        newCustomer.getFirstName()));
        try {
            rabbitMqMessageProducer.publish(notificationRequest,
                    "internal.exchange", "internal.notification.routing-key");
        } catch (Exception e) {
            notificationClient.sendNotification(
                    new NotificationRequest(
                            newCustomer.getId(),
                            newCustomer.getEmail(),
                            String.format("Hi %s, welcome to Amigoscode...",
                                    newCustomer.getFirstName())
                    ));
        }
    }
}
