package com.appsdeveloperblog.estore.OrdersService.event;

import com.appsdeveloperblog.estore.OrdersService.core.model.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderApprovedEvent {
    String orderId;
    OrderStatus orderStatus;
}
