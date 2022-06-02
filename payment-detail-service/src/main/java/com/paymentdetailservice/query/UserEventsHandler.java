/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentdetailservice.query;

import com.communicationcorelibrary.communicationcorelibrary.model.PaymentDetails;
import com.communicationcorelibrary.communicationcorelibrary.model.User;
import com.communicationcorelibrary.communicationcorelibrary.query.FetchUserPaymentDetailsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

 

@Component
public class UserEventsHandler {

    @QueryHandler
    public User findUserPaymentDetails(FetchUserPaymentDetailsQuery query) {
        
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .cardNumber("123Card")
                .cvv("123")
                .name("IVAN PYLNEV")
                .validUntilMonth(12)
                .validUntilYear(2030)
                .build();

        return User.builder()
                .firstName("Ivan")
                .lastName("Pylnev")
                .userId(query.getUserId())
                .paymentDetails(paymentDetails)
                .build();
    }
    
    
}
