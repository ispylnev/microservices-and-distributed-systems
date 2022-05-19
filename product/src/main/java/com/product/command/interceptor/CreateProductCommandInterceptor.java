package com.product.command.interceptor;

import com.product.command.CreateProductCommand;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

@Component
public class CreateProductCommandInterceptor
        implements MessageDispatchInterceptor<CommandMessage<?>> {

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>>
    handle(List<? extends CommandMessage<?>> list) {
        return (index, command) -> {
            if (CreateProductCommand.class.equals(command.getPayloadType())) {
                CreateProductCommand createProductCommand = (CreateProductCommand) command.getPayload();
                if (createProductCommand.getTitle().toCharArray().length == 3) {
                    throw new IllegalArgumentException("too short title name");
                }
            }
            return command;
        };
    }
}
