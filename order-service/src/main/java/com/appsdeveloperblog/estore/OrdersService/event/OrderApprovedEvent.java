package com.appsdeveloperblog.estore.OrdersService.event;

import com.communicationcorelibrary.communicationcorelibrary.model.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderApprovedEvent {
    String orderId;
    OrderStatus orderStatus;
}
