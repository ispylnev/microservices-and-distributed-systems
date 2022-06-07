package com.communicationcorelibrary.communicationcorelibrary.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductReservationCancelledEvent {
    private final String productId;
    private final String orderId;
    private final String userId;
    private final String reason;
    private final int quantity;
}
