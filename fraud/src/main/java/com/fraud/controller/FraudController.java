package com.fraud.controller;

import com.fraud.FraudCheckService;
import com.fraud.dto.FraudCheckResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/fraud-ckeck")
public record FraudController(FraudCheckService fraudCheckService) {

    @GetMapping(path = {"customerId"})
    public FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerId) {
        boolean isFraudulentCustomer = fraudCheckService.isFraudulentCustomer(customerId);
        return new FraudCheckResponse(isFraudulentCustomer);

    }
}
