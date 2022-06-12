package com.appsdeveloperblog.estore.OrdersService.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindOrderQuery {
    private final String orderId;
}
