package com.communicationcorelibrary.communicationcorelibrary.model;

import lombok.Data;

@Data
public class OrderSummary {
    private final String orderId;
    private final OrderStatus orderStatus;
}
