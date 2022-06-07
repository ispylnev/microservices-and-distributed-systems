package com.appsdeveloperblog.estore.OrdersService.saga;

import com.appsdeveloperblog.estore.OrdersService.command.RejectOrderCommand;
import com.appsdeveloperblog.estore.OrdersService.command.commands.ApprovedOrderCommand;
import com.appsdeveloperblog.estore.OrdersService.core.events.OrderCreatedEvent;
import com.appsdeveloperblog.estore.OrdersService.core.events.OrderRejectedEvent;
import com.appsdeveloperblog.estore.OrdersService.event.OrderApprovedEvent;
import com.communicationcorelibrary.communicationcorelibrary.command.CancelProductReservationCommand;
import com.communicationcorelibrary.communicationcorelibrary.command.ProcessPaymentCommand;
import com.communicationcorelibrary.communicationcorelibrary.command.ReserveProductCommand;
import com.communicationcorelibrary.communicationcorelibrary.event.PaymentProcessedEvent;
import com.communicationcorelibrary.communicationcorelibrary.event.ProductReservationCancelledEvent;
import com.communicationcorelibrary.communicationcorelibrary.event.ProductReservedEvent;
import com.communicationcorelibrary.communicationcorelibrary.model.User;
import com.communicationcorelibrary.communicationcorelibrary.query.FetchUserPaymentDetailsQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
            paymentDetails = queryGateway.query(fetchUserPaymentDetailsQuery, ResponseTypes.instanceOf(User.class)).join();
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("compensation transaction has started");
            cancelProductReservation(productReservedEvent, e.getMessage());
            // start compensation transaction
            return;
        }
        if (fetchUserPaymentDetailsQuery == null) {
            log.error("compensation transaction has started. Because the FetchUserPaymentDetailsQuery is null");
            cancelProductReservation(productReservedEvent, "fetchUserPaymentDetailsQuery is null");
            return;
        }
        log.info("user details  has been fetched successfully");

        ProcessPaymentCommand processPaymentCommand = ProcessPaymentCommand.builder()
                .orderId(productReservedEvent.getOrderId())
                .paymentDetails(paymentDetails.getPaymentDetails())
                .paymentId(UUID.randomUUID().toString())
                .build();
        String result = null;
        try {
            result = commandGateway.sendAndWait(processPaymentCommand, 3, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.info("ProcessPayment has been failed");
            cancelProductReservation(productReservedEvent, e.getMessage());
        }
        if (result == null) {
            log.error("compensation transaction has started. Because the ProcessPaymentCommand is null");
            cancelProductReservation(productReservedEvent, "Could not process user payment " +
                    "with provided payment information");
        }
    }

    private void cancelProductReservation(ProductReservedEvent event, String reason) {
        CancelProductReservationCommand command = CancelProductReservationCommand.builder()
                .orderId(event.getOrderId())
                .productId(event.getProductId())
                .reason(reason)
                .userId(event.getUserId())
                .quantity(event.getQuantity())
                .build();
        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent processedEvent) {
        ApprovedOrderCommand approvedOrderCommand =
                new ApprovedOrderCommand(processedEvent.getOrderId());
        commandGateway.send(approvedOrderCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void handle(OrderApprovedEvent orderApprovedEvent) {
        log.info("order is approved. Order saga is complete for orderId. :{} ",
                orderApprovedEvent.getOrderId());
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservationCancelledEvent event) {
        RejectOrderCommand command =
                new RejectOrderCommand(event.getOrderId(), event.getReason());
        commandGateway.send(command);
    }
    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void handle(OrderRejectedEvent event) {
        log.info("Successfully rejected order with id" + event.getOrderId());
    }
}
