package com.appsdeveloperblog.estore.OrdersService.saga;

import com.appsdeveloperblog.estore.OrdersService.core.events.OrderCreatedEvent;
import com.communicationcorelibrary.communicationcorelibrary.command.ReserveProductCommand;
import com.communicationcorelibrary.communicationcorelibrary.event.ProductReservedEvent;
import com.communicationcorelibrary.communicationcorelibrary.model.User;
import com.communicationcorelibrary.communicationcorelibrary.query.FetchUserPaymentDetailsQuery;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Saga
@Component
@Slf4j
public class OrderSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent orderCreatedEvent) {
        ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
                .orderId(orderCreatedEvent.getOrderId())
                .productId(orderCreatedEvent.getProductId())
                .userId(orderCreatedEvent.getUserId())
                .quantity(orderCreatedEvent.getQuantity())
                .build();
        commandGateway.send(reserveProductCommand, new CommandCallback<ReserveProductCommand, Object>() {
            @Override
            public void onResult(CommandMessage<? extends ReserveProductCommand> commandMessage, CommandResultMessage<?> commandResultMessage) {
                if (commandResultMessage.isExceptional()) {

                }
            }
        });
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent productReservedEvent) {
        log.info("ProductReservedEvent is called for productId = "
                + productReservedEvent.getProductId() +
                " and orderId" + productReservedEvent.getUserId());

        FetchUserPaymentDetailsQuery fetchUserPaymentDetailsQuery =
                new FetchUserPaymentDetailsQuery(productReservedEvent.getUserId());
        User paymentDetails = null;
        try {
            queryGateway.query(fetchUserPaymentDetailsQuery, ResponseTypes.instanceOf(User.class)).join();
        } catch (Exception e){
            log.error(e.getMessage());
            // start compensation transaction
            return;
        }
        if (fetchUserPaymentDetailsQuery == null) {
            // start compensation transaction
            return;
        }
        log.info("user details  has been fetched successfully");
    }
}
