package com.appsdeveloperblog.estore.OrdersService;

import com.appsdeveloperblog.estore.OrdersService.command.RejectOrderCommand;
import com.appsdeveloperblog.estore.OrdersService.command.commands.ApprovedOrderCommand;
import com.appsdeveloperblog.estore.OrdersService.core.events.OrderCreatedEvent;
import com.appsdeveloperblog.estore.OrdersService.core.events.OrderRejectedEvent;
import com.appsdeveloperblog.estore.OrdersService.core.model.OrderStatus;
import com.appsdeveloperblog.estore.OrdersService.command.commands.CreateOrderCommand;
import com.appsdeveloperblog.estore.OrdersService.event.OrderApprovedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private String productId;
    private String userId;
    private int quantity;
    private String addressId;
    private OrderStatus orderStatus;

    public OrderAggregate() {
    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);

        AggregateLifecycle.apply(orderCreatedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        this.orderId = orderCreatedEvent.getOrderId();
        this.productId = orderCreatedEvent.getProductId();
        this.userId = orderCreatedEvent.getUserId();
        this.addressId = orderCreatedEvent.getAddressId();
        this.quantity = orderCreatedEvent.getQuantity();
        this.orderStatus = orderCreatedEvent.getOrderStatus();
    }

    @CommandHandler
    public void handle(ApprovedOrderCommand command) {
        OrderApprovedEvent orderApprovedEvent =
                new OrderApprovedEvent();
        orderApprovedEvent.setOrderId(command.getOrderId());
        AggregateLifecycle.apply(orderApprovedEvent);
    }

    @CommandHandler
    public void handle(RejectOrderCommand command) {
        OrderRejectedEvent event =
                new OrderRejectedEvent(command.getOrderId(), command.getReason());
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(OrderRejectedEvent event) {
        this.orderStatus = event.getOrderStatus();
    }

    @EventSourcingHandler
    public void on(OrderApprovedEvent orderApprovedEvent) {
        this.orderStatus = orderApprovedEvent.getOrderStatus();
    }


}
