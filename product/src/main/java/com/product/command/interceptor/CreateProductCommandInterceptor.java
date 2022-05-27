package com.product.command.interceptor;

import com.product.command.CreateProductCommand;
import com.product.core.entity.ProductLookupEntity;
import com.product.core.repository.ProductLookupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateProductCommandInterceptor
        implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final ProductLookupRepository productLookupRepository;

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>>
    handle(List<? extends CommandMessage<?>> list) {
        return (index, command) -> {
            if (CreateProductCommand.class.equals(command.getPayloadType())) {
                log.info("Interceptor command:" + command.getPayloadType());
                CreateProductCommand createProductCommand = (CreateProductCommand) command.getPayload();
                ProductLookupEntity entity = productLookupRepository
                        .findProductLookupEntityByProductionIdOrTitle(createProductCommand.getProductId(),
                                createProductCommand.getTitle());
                if (entity != null) {
                    throw new IllegalStateException("Product already exist");
                }
            }
            return command;
        };
    }
}
