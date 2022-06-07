package com.communicationcorelibrary.communicationcorelibrary.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CancelProductReservationCommand {

    @TargetAggregateIdentifier
    private final String productId;
    private final String orderId;
    private String userId;
    private final String reason;
    private int quantity;
}
