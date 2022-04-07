package com.fraud.service;

import com.fraud.entity.FraudCheckHistory;
import com.fraud.repository.FraudCheckHistoryRepository;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@EnableEurekaClient
public record FraudCheckService(FraudCheckHistoryRepository fraudCheckHistoryRepository) {

    public boolean isFraudulentCustomer(Integer customerId) {
        fraudCheckHistoryRepository.save(FraudCheckHistory.builder()
                .isFraudster(false)
                .customerId(customerId)
                .createdAt(LocalDateTime.now())
                .build());
        return false;
    }
}
