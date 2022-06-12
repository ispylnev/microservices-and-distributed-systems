package com.appsdeveloperblog.estore.OrdersService.core.events;

import com.communicationcorelibrary.communicationcorelibrary.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderRejectedEvent {
    private final String orderId;
    private final String reason;
    private final OrderStatus orderStatus = OrderStatus.REJECTED;
}
