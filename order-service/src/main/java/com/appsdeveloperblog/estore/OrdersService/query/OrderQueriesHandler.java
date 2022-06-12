package com.appsdeveloperblog.estore.OrdersService.query;

import com.appsdeveloperblog.estore.OrdersService.core.data.OrderEntity;
import com.appsdeveloperblog.estore.OrdersService.core.data.OrdersRepository;
import com.communicationcorelibrary.communicationcorelibrary.model.OrderSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderQueriesHandler {
    private final OrdersRepository ordersRepository;

    public OrderSummary findOrder(FindOrderQuery findOrderQuery) {
        OrderEntity orderEntity =
                ordersRepository.findOrderEntityByOrderId(findOrderQuery.getOrderId());
        return new OrderSummary(orderEntity.getOrderId(), orderEntity.getOrderStatus());
    }
}
